package com.kaique.gerenciamentovendas.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaique.gerenciamentovendas.dtos.EmailDTO;
import com.kaique.gerenciamentovendas.security.JWTUtil;
import com.kaique.gerenciamentovendas.security.UserSS;
import com.kaique.gerenciamentovendas.services.AuthService;
import com.kaique.gerenciamentovendas.services.UserService;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@PostMapping(value="/refresh_token")
	public ResponseEntity<Void> refreshToken (HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value="/forgot")
	public ResponseEntity<Void> forgot (@Valid @RequestBody EmailDTO objDto) {
		this.authService.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}
}