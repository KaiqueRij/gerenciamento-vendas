package com.kaique.gerenciamentovendas.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaique.gerenciamentovendas.model.Cliente;
import com.kaique.gerenciamentovendas.repositorys.ClienteRepository;
import com.kaique.gerenciamentovendas.services.exceptions.ObjetoNaoEncontradoException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword (String email) {
		
		Cliente cliente = this.clienteRepository.findByEmail(email);
		
		if (cliente == null) {
			throw new ObjetoNaoEncontradoException("E-mail não encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(this.bCryptPasswordEncoder.encode(newPass));
		
		this.clienteRepository.save(cliente);
		this.emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		
		int opt = this.rand.nextInt(3);
		
		if (opt == 0) { // Gera um número
			return (char) (this.rand.nextInt(10) + 48);
		} else if (opt == 1) { // Gera letra maiúscula
			return (char) (this.rand.nextInt(26) + 65);
		} else {  // Gera letra minúscula
			return (char) (this.rand.nextInt(26) + 97);
		}
	}	
}
