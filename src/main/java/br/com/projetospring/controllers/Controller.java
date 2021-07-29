package br.com.projetospring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/usuario")
public class Controller {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<?> consultarUsuario(@PathVariable Long id){
		
		if (id != null) {
			Usuario usuario = usuarioRepository.findById(id).get();
			if (usuario != null) {
				return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("Usuário não encontrato", HttpStatus.NO_CONTENT);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping(value = "/lista", produces = "application/json")
	public ResponseEntity<List<Usuario>> lista(){
		List<Usuario> lista = usuarioRepository.findAll();
		
		if (lista.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Usuario>>(lista, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario){
		
		if (usuario != null) {
		
			String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getPassword());
			
			usuario.setSenha(senhaCriptografada);
			
			for (int i = 0; i < usuario.getTelefones().size(); i++) {
				usuario.getTelefones().get(i).setUsuario(usuario);
			}
			
			Usuario usuarioSalvo = usuarioRepository.save(usuario);
			return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping(value = "/")
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario){
		
		
		if (usuario.getId() == null) {
			return new ResponseEntity<String>("ID não informado", HttpStatus.BAD_REQUEST);
		} else {
			Usuario usuarioTemporario = usuarioRepository.findUsuarioByLogin(usuario.getLogin());
			
			if (!usuarioTemporario.getSenha().equals(usuario.getSenha())) {
				String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
				usuario.setSenha(senhaCriptografada);
			}
			
			for (int i = 0; i < usuario.getTelefones().size(); i++) {
				usuario.getTelefones().get(i).setUsuario(usuario);
			}
		
			
			Usuario usuarioAtualizado = usuarioRepository.save(usuario);
			return new ResponseEntity<Usuario>(usuarioAtualizado, HttpStatus.OK);
		}
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/text")
	public ResponseEntity<?> excluir(@PathVariable Long id){
		
		if (id != null) {
			usuarioRepository.deleteById(id);
			return new ResponseEntity<String>("Usuário excluído com sucesso", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		
		
		
	}
}
