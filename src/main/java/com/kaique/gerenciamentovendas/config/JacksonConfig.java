package com.kaique.gerenciamentovendas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaique.gerenciamentovendas.model.PagamentoComBoleto;
import com.kaique.gerenciamentovendas.model.PagamentoComCartao;

@Configuration
public class JacksonConfig {
	
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder () {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder () {
			public void configure (ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(PagamentoComCartao.class);
				objectMapper.registerSubtypes(PagamentoComBoleto.class);
				super.configure(objectMapper);
			}
		};
		return builder;
	}
}
