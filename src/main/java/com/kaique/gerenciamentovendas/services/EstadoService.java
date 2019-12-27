package com.kaique.gerenciamentovendas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaique.gerenciamentovendas.model.Estado;
import com.kaique.gerenciamentovendas.repositorys.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Estado> findAll () {
		return this.estadoRepository.findAllByOrderByNome();
	}
}
