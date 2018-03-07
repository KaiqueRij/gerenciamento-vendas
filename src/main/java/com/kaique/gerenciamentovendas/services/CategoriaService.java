package com.kaique.gerenciamentovendas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.kaique.gerenciamentovendas.model.Categoria;
import com.kaique.gerenciamentovendas.repositorys.CategoriaRepository;
import com.kaique.gerenciamentovendas.services.exceptions.IntegridadeDaInformacaoException;
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
	
	public List<Categoria> findAll(){
		return this.categoriaRepository.findAll();
	}
	
	public Categoria insert(Categoria obj){
		obj.setId(null);
		return this.categoriaRepository.save(obj);
	}
	
	public Categoria update(Categoria obj){
		getCategoriaById(obj.getId());
		return this.categoriaRepository.save(obj);		
	}
	
	public void delete(Integer id){
		getCategoriaById(id);
		try {
			this.categoriaRepository.delete(id);			
		} catch (Exception e) {
			throw new IntegridadeDaInformacaoException("Não é possível deletar categorias que possui produtos.");
		}
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return this.categoriaRepository.findAll(pageRequest);
	}

}
