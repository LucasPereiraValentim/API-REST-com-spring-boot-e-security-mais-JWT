package br.com.projetospring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.projetospring.models.Usuario;
import br.com.projetospring.repositories.UsuarioRepository;

@Service
public class ImplementsUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//Consultar no banco o usuário
		
		Usuario usuario = usuarioRepository.findUsuarioByLogin(username);
		
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		} else {
			return new User(
					usuario.getLogin(),
					usuario.getSenha(), 
					usuario.getAuthorities());
		}
		
		
		
	}
	
}
