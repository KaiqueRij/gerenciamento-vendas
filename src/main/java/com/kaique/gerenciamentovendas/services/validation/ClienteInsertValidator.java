package com.kaique.gerenciamentovendas.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.kaique.gerenciamentovendas.dtos.ClienteNewDTO;
import com.kaique.gerenciamentovendas.model.Cliente;
import com.kaique.gerenciamentovendas.model.enums.TipoCliente;
import com.kaique.gerenciamentovendas.repositorys.ClienteRepository;
import com.kaique.gerenciamentovendas.resources.exceptions.FieldMessage;
import com.kaique.gerenciamentovendas.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert constraintAnnotation) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean isValid(ClienteNewDTO value, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if (value.getTipo().equals(TipoCliente.PESSOAFISICA.getCodigo())
				&& !BR.isValidCPF(value.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido"));
		}
		
		if (value.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCodigo())
				&& !BR.isValidCNPJ(value.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido"));
		}
		
		Cliente cliente = this.clienteRepository.findByEmail(value.getEmail());
		if (cliente != null) {
			list.add(new FieldMessage("email", "E-mail já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
				.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		
		return list.isEmpty();
	}	
}
