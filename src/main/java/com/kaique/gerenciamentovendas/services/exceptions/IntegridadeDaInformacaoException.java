package com.kaique.gerenciamentovendas.services.exceptions;

public class IntegridadeDaInformacaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public IntegridadeDaInformacaoException(String mensagem) {
		super(mensagem);
	}
	
	public IntegridadeDaInformacaoException(String mensagem, Throwable causa){
		super(mensagem, causa);
	}	
}
