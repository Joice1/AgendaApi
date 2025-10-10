package br.com.cotiinformatica.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.cotiinformatica.filters.JwtAuthFilter;

@Configuration
public class FilterConfiguration {

	@Value("${chave_jwt}")
	private String chaveJwt;
	
	@Bean
	FilterRegistrationBean<JwtAuthFilter> jwtAuthFilter() {
		
		//Registrando o filter criado
		FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>();
		
		//Configurando a classe filter
		registration.setFilter(new JwtAuthFilter(chaveJwt));
		
		//Configurando os endpoints que serão verificados pelo Filter
		registration.addUrlPatterns("/api/*");
		
		//retornando o filter
		return registration;
	}
}
