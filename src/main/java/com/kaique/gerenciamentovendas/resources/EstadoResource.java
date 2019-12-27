package com.kaique.gerenciamentovendas.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaique.gerenciamentovendas.dtos.CidadeDTO;
import com.kaique.gerenciamentovendas.dtos.EstadoDTO;
import com.kaique.gerenciamentovendas.model.Cidade;
import com.kaique.gerenciamentovendas.model.Estado;
import com.kaique.gerenciamentovendas.services.CidadeService;
import com.kaique.gerenciamentovendas.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping
	public ResponseEntity<List<EstadoDTO>> findAll () {
		List<Estado> list = this.estadoService.findAll();
		List<EstadoDTO> listDto = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}	
	
	@GetMapping(value="/{estadoId}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidades (@PathVariable Integer estadoId) {
		List<Cidade> list = this.cidadeService.findByEstado(estadoId);
		List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
}
