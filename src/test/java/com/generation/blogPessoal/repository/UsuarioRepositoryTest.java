package com.generation.blogPessoal.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;


import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogPessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		usuarioRepository.save(new Usuario(0L, "Glaucya Nascimento", "glaucya@email.com", "13465278"));
		usuarioRepository.save(new Usuario(0L, "Carlos Nascimento", "carlos@email.com", "13465278"));
		usuarioRepository.save(new Usuario(0L, "Cauan Nascimento", "cauan@email.com", "13465278" ));
		usuarioRepository.save(new Usuario(0L, "gleicy", "gleicy@email.com", "13465278"));
		
	}
	@Test
	@DisplayName("Retorna 1 usuario")
	public void deveRetornarUmUsuario() {
		
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("glaucya@email.com");
		assertTrue(usuario.get().getUsuario().equals("glaucya@email.com"));
		
	}
	@Test
	@DisplayName("retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Nascimento");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("Carlos Nascimento"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Cauan Nascimento"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Glaucya Nascimento"));
	}
	
	
}
