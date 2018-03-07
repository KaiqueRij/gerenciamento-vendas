package com.kaique.gerenciamentovendas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaique.gerenciamentovendas.model.Categoria;
import com.kaique.gerenciamentovendas.repositorys.CategoriaRepository;
import com.kaique.gerenciamentovendas.services.exceptions.ObjetoNaoEncontradoException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria getCategoriaById(Integer id){
		Categoria categoria = this.categoriaRepository.findOne(id);
		
		if(categoria == null){
			throw new ObjetoNaoEncontradoException("Objeto não encontrado.");
		}
		
		return categoria;
	}
	
	public Categoria insert(Categoria obj){
		obj.setId(null);
		return this.categoriaRepository.save(obj);
	}
	
	public Categoria update(Categoria obj){
		getCategoriaById(obj.getId());
		return this.categoriaRepository.save(obj);		
	}

}
