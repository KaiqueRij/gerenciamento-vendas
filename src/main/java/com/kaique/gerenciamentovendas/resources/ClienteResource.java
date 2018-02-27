package com.kaique.gerenciamentovendas.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaique.gerenciamentovendas.model.Cliente;
import com.kaique.gerenciamentovendas.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clientes;
	
	@GetMapping(value = "{id}")
	public ResponseEntity<?> getClienteById(@PathVariable Integer id){
		Cliente cliente = this.clientes.getClienteById(id);
		return ResponseEntity.ok(cliente);
	}
	

}
