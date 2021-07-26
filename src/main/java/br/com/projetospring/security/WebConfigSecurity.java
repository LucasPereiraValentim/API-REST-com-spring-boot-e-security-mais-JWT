package br.com.projetospring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.projetospring.services.ImplementacaoUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImplementacaoUserDetailsService detailsService;
	
	//solicitações de acesso
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//Ativando a proteçao contra usuários que não estão validados por token
		http.csrf().disable()
		.authorizeRequests().antMatchers("/index").permitAll()
		//URL de logout - Redireciona para pag de login
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		//Mapeia URL de logout e invalida os dados do usuário
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		//Filtra requisições de login para autenticação
		.and().addFilterBefore(new JwtLoginFilter("/login", authenticationManager()),
				UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//Service que consulta o usuário no banco de dados
		auth.userDetailsService(detailsService)
		//Codificação de senha para o usuário
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	
	
}
