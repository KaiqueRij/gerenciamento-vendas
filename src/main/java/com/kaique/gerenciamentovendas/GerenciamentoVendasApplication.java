package com.kaique.gerenciamentovendas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kaique.gerenciamentovendas.services.S3Service;

@SpringBootApplication
public class GerenciamentoVendasApplication implements CommandLineRunner{
	
	@Autowired
	private S3Service s3Service;

	public static void main(String[] args) {
		SpringApplication.run(GerenciamentoVendasApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		this.s3Service.uploadFile("/home/kaique/Imagens/teste.jpeg");
	}
}
