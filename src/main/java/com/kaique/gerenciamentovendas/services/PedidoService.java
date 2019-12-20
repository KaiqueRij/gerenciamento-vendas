package com.kaique.gerenciamentovendas.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaique.gerenciamentovendas.model.ItemPedido;
import com.kaique.gerenciamentovendas.model.PagamentoComBoleto;
import com.kaique.gerenciamentovendas.model.Pedido;
import com.kaique.gerenciamentovendas.model.enums.EstadoPagamento;
import com.kaique.gerenciamentovendas.repositorys.ItemPedidoRepository;
import com.kaique.gerenciamentovendas.repositorys.PagamentoRepository;
import com.kaique.gerenciamentovendas.repositorys.PedidoRepository;
import com.kaique.gerenciamentovendas.services.exceptions.ObjetoNaoEncontradoException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	public Pedido getPedidoById(Integer id){
		Pedido pedido = this.pedidoRepository.findOne(id);
		
		if(pedido == null){
			throw new ObjetoNaoEncontradoException("Objeto não encontrado.");
		}
		
		return pedido;
	}
	
	public Pedido insert (Pedido obj) {
		obj.setId(null);
		obj.setInstance(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstance());
		}
		
		obj = this.pedidoRepository.save(obj);
		this.pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(this.produtoService.getProdutoById(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		
		this.itemPedidoRepository.save(obj.getItens());
		return obj;
	}
}
