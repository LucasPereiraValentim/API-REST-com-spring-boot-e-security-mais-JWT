package br.com.projetospring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.projetospring.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
}
