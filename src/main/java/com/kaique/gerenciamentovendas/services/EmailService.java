package com.kaique.gerenciamentovendas.services;

import org.springframework.mail.SimpleMailMessage;

import com.kaique.gerenciamentovendas.model.Pedido;

public interface EmailService {
	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg);
}
