package com.generation.blogPessoal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogPessoal.model.Postagem;
import com.generation.blogPessoal.repository.postagemRepository;

@RestController
@RequestMapping ("/postagens")
@CrossOrigin("*")
public class postagemController {
	
	@Autowired
	private postagemRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> GetAll(){
		return ResponseEntity.ok(repository.findAll());	
		}
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> GetById(@PathVariable long id){
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> GetByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping
	public ResponseEntity<Postagem> post (@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
		
	}
	@PutMapping
	public ResponseEntity<Postagem> put (@Valid @RequestBody Postagem postagem){
		return repository.findById(postagem.getId())
				.map(resp-> ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem)))
				.orElseGet(() -> {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"id não encontrado");
				});
		
	}
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable long id) {
		return repository.findById(id).map(response ->{
			repository.deleteById(id);
			return ResponseEntity.status(200).build();
		}).orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
		
		
	}

}
