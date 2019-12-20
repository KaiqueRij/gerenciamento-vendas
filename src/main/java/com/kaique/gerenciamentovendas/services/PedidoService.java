package com.kaique.gerenciamentovendas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaique.gerenciamentovendas.model.Pedido;
import com.kaique.gerenciamentovendas.repositorys.PedidoRepository;
import com.kaique.gerenciamentovendas.services.exceptions.ObjetoNaoEncontradoException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido getPedidoById(Integer id){
		Pedido pedido = this.pedidoRepository.findOne(id);
		
		if(pedido == null){
			throw new ObjetoNaoEncontradoException("Objeto não encontrado.");
		}
		
		return pedido;
	}
}
