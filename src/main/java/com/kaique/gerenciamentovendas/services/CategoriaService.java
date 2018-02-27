package com.kaique.gerenciamentovendas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaique.gerenciamentovendas.model.Categoria;
import com.kaique.gerenciamentovendas.repositorys.CategoriaRepository;
import com.kaique.gerenciamentovendas.services.exceptions.ObjetoNaoEncontradoException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categorias;
	
	public Categoria getCategoriaById(Integer id){
		Categoria categoria = this.categorias.findOne(id);
		
		if(categoria == null){
			throw new ObjetoNaoEncontradoException("Objeto não encontrado.");
		}
		
		return categoria;
	}

}
