package com.kaique.gerenciamentovendas.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaique.gerenciamentovendas.model.Categoria;
import com.kaique.gerenciamentovendas.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categorias;
	
	@GetMapping(value = "{id}")
	public ResponseEntity<?> getCategoriaById(@PathVariable Integer id){
		Categoria categoria = this.categorias.getCategoriaById(id);
		return ResponseEntity.ok(categoria);
	}
	

}
