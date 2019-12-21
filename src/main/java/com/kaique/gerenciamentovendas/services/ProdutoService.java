package com.kaique.gerenciamentovendas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.kaique.gerenciamentovendas.model.Categoria;
import com.kaique.gerenciamentovendas.model.Produto;
import com.kaique.gerenciamentovendas.repositorys.CategoriaRepository;
import com.kaique.gerenciamentovendas.repositorys.ProdutoRepository;
import com.kaique.gerenciamentovendas.services.exceptions.ObjetoNaoEncontradoException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
 	
	public Produto getProdutoById(Integer id){
		Produto pedido = this.produtoRepository.findOne(id);
		
		if(pedido == null){
			throw new ObjetoNaoEncontradoException("Objeto não encontrado.");
		}
		
		return pedido;
	}
	
	public Produto find (Integer id) {
		Produto pedido = this.produtoRepository.findOne(id);
		
		if(pedido == null){
			throw new ObjetoNaoEncontradoException("Objeto não encontrado.");
		}
		
		return pedido;
	}
	
	public Page<Produto> search (String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAll(ids);
		return this.produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
