package br.com.projetospring.services;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.projetospring.ApplicationContextLoad;
import br.com.projetospring.models.Usuario;
import br.com.projetospring.repositories.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JwtTokenAutenticacaoService {
	
	//Tempo de duração do TOKEN
	private static final long EXPIRATION_TIME = 172800000;
	
	//Senha unica para compor a autenticação
	private static final String SECRET = "SENHASECRETA";
	
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	
	//Gerando TOKEN de autenticação e retornando ao corbo da resposta
	public void addAuthentication(HttpServletResponse response, String userName) throws Exception{
		
		String jwt = Jwts.builder() //Chama o gerador de TOKEN
				.setSubject(userName) // add usuário
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // tempo de expiração
				.signWith(SignatureAlgorithm.HS512, SECRET).compact(); // compacta e gera o TOKEN
		
		//Concatena TOKEN com PREFIX
		String token = TOKEN_PREFIX + " " + jwt;
		
		//Add no cabeçalho htto
		response.addHeader(HEADER_STRING, token);
		
		response.getWriter().write("{\"Authorization\": \""+ token + "\"}");;
		
		
	}
	
	
	//Retorna usuário validado com TOKEN 
	public Authentication getAuthentication(HttpServletRequest request) {
		
		//Pega o TOKEN enviado ao cabeçalho http
		
		String token = request.getHeader(HEADER_STRING);
		
		if (token != null) {
			 // faz a validação do TOKEN do usuário na requisição
			
			String user = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, "")) // Retorna apenas o TOKEN
					.getBody().getSubject(); //Retorna o usuário
			if (user != null) {
				Usuario usuario = ApplicationContextLoad
						.getApplicationContext().getBean(UsuarioRepository.class).findUsuarioByLogin(user);
				if (usuario != null) {
					return new UsernamePasswordAuthenticationToken
							(usuario.getLogin(), usuario.getSenha(), usuario.getAuthorities());
				}
			}
		}
		return null;
	}
	
}
