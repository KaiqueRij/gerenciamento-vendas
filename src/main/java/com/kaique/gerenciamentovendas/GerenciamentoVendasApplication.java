package com.kaique.gerenciamentovendas;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kaique.gerenciamentovendas.model.Categoria;
import com.kaique.gerenciamentovendas.model.Produto;
import com.kaique.gerenciamentovendas.repositorys.CategoriaRepository;
import com.kaique.gerenciamentovendas.repositorys.ProdutoRepository;

@SpringBootApplication
public class GerenciamentoVendasApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(GerenciamentoVendasApplication.class, args);
	}
	
	@Autowired
	private CategoriaRepository categorias;
	@Autowired
	private ProdutoRepository produtos;

	@Override
	public void run(String... arg0) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		this.categorias.save(Arrays.asList(cat1, cat2));
		this.produtos.save(Arrays.asList(p1, p2, p3));
	}
}
