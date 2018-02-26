package com.kaique.gerenciamentovendas;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kaique.gerenciamentovendas.model.Categoria;
import com.kaique.gerenciamentovendas.repositorys.CategoriaRepository;

@SpringBootApplication
public class GerenciamentoVendasApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(GerenciamentoVendasApplication.class, args);
	}
	
	@Autowired
	private CategoriaRepository categorias;

	@Override
	public void run(String... arg0) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		this.categorias.save(Arrays.asList(cat1, cat2));
		
	}
}
