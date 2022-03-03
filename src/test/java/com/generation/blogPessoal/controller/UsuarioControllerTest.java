package com.generation.blogPessoal.controller;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogPessoal.model.Usuario;
import com.generation.blogPessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	@Autowired private TestRestTemplate testRestTemplate;
	@Autowired private UsuarioService usuarioService;
	
	
	@Test
	@Order(1)
	@DisplayName("Cadastrar Um Usuario")
	public void deveCriarUmUsuario(){
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L,
				"Henrique Savioli", "hsavioli@email.com", "13465278"));
		ResponseEntity<Usuario> resposta = testRestTemplate
				.exchange( "/usuarios/cadastrar",HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
	}

	@Test
	@Order(2)
	@DisplayName("Não Deve permitir duplicação de usuario")
	public void naoDeveDuplicarUsuario(){
		
		usuarioService.CadastrarUsuario(new Usuario(0L,
				"Maria Savioli", "mariasavioli@email.com", "13465278"));
		
		HttpEntity<Usuario> requisicao =new HttpEntity<Usuario>(new Usuario(0L,
				"Maria Savioli", "mariasavioli@email.com", "13465278"));
		
		ResponseEntity<Usuario> resposta = testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.UNAUTHORIZED, resposta.getStatusCode());
	}
	
	@Disabled
	@Test
	@Order(4)
	@DisplayName("Listar todos os Usuarios")
	public void deveMosrarTodosUsuarios(){
		usuarioService.CadastrarUsuario(new Usuario(0L, 
				"Pedro Sampaio", "pedro2022@email.com.br", "13465278"));
			
			usuarioService.CadastrarUsuario(new Usuario(0L, 
				"Pedro da Silva Sampaio", "pedro2022@email.com.br", "13465278"));

			ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/listar", HttpMethod.GET, null, String.class);

			assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
}



	


