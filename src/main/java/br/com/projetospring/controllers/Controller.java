package br.com.projetospring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projetospring.models.Usuario;
import br.com.projetospring.repositories.UsuarioRepository;


@RestController
@RequestMapping(value = "/usuario")
public class Controller {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping(value = "{id}", produces = "application/json")
	public ResponseEntity<Usuario> consultarUsuario(@PathVariable Long id){
		
		Usuario usuario = usuarioRepository.findById(id).get();
		
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/lista", produces = "application/json")
	public ResponseEntity<List<Usuario>> lista(){
		
		List<Usuario> lista = usuarioRepository.findAll();
		
		return new ResponseEntity<List<Usuario>>(lista, HttpStatus.OK);
	}
	
	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario){
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/")
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario){
		
		
		if (usuario.getId() == null) {
			return new ResponseEntity<String>("ID não informado", HttpStatus.NOT_FOUND);
		}
		
		Usuario usuarioAtualizado = usuarioRepository.save(usuario);
		
		
		return new ResponseEntity<Usuario>(usuarioAtualizado, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/text")
	public ResponseEntity<?> excluir(@PathVariable Long id){
		
		usuarioRepository.deleteById(id);
		
		
		return new ResponseEntity<String>("Usuário excluído com sucesso", HttpStatus.OK);
	}
}
