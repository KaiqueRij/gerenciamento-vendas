package com.kaique.gerenciamentovendas;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kaique.gerenciamentovendas.model.Categoria;
import com.kaique.gerenciamentovendas.model.Cidade;
import com.kaique.gerenciamentovendas.model.Cliente;
import com.kaique.gerenciamentovendas.model.Endereco;
import com.kaique.gerenciamentovendas.model.Estado;
import com.kaique.gerenciamentovendas.model.Produto;
import com.kaique.gerenciamentovendas.model.enums.TipoCliente;
import com.kaique.gerenciamentovendas.repositorys.CategoriaRepository;
import com.kaique.gerenciamentovendas.repositorys.CidadeRepository;
import com.kaique.gerenciamentovendas.repositorys.ClienteRepository;
import com.kaique.gerenciamentovendas.repositorys.EnderecoRepository;
import com.kaique.gerenciamentovendas.repositorys.EstadoRepository;
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
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;

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
		
		Estado est1 = new Estado(null, 	"Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est1);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		this.estadoRepository.save(Arrays.asList(est1, est2));
		this.cidadeRepository.save(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "3333333", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("232323", "242424"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "5544554", 
				cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "3352232", 
				cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		this.clienteRepository.save(Arrays.asList(cli1));
		this.enderecoRepository.save(Arrays.asList(e1, e2));
		
	}
}
