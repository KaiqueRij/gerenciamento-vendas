package com.kaique.gerenciamentovendas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.kaique.gerenciamentovendas.dtos.ClienteDTO;
import com.kaique.gerenciamentovendas.model.Cliente;
import com.kaique.gerenciamentovendas.repositorys.ClienteRepository;
import com.kaique.gerenciamentovendas.services.exceptions.IntegridadeDaInformacaoException;
import com.kaique.gerenciamentovendas.services.exceptions.ObjetoNaoEncontradoException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente getClienteById(Integer id){
		Cliente categoria = this.clienteRepository.findOne(id);
		
		if(categoria == null){
			throw new ObjetoNaoEncontradoException("Objeto não encontrado.");
		}
		
		return categoria;
	}
	
	public List<Cliente> findAll(){
		return this.clienteRepository.findAll();
	}
	
	public Cliente update(Cliente obj){
		Cliente newObj = getClienteById(obj.getId());
		updateData(newObj, obj);
		return this.clienteRepository.save(newObj);		
	}
	
	public void delete(Integer id){
		getClienteById(id);
		try {
			this.clienteRepository.delete(id);			
		} catch (Exception e) {
			throw new IntegridadeDaInformacaoException("Não é possível excluir porque há entidades relacionadas.");
		}
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return this.clienteRepository.findAll(pageRequest);
	}
	
	public Cliente fromDto(ClienteDTO objDto){
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj){
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
