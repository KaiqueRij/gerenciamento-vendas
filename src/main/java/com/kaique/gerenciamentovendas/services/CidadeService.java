package com.kaique.gerenciamentovendas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaique.gerenciamentovendas.model.Cidade;
import com.kaique.gerenciamentovendas.repositorys.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public List<Cidade> findByEstado (Integer estadoId) {
		return this.cidadeRepository.findCidades(estadoId);
	}
}
