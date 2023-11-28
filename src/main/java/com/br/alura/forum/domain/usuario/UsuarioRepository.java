package com.br.alura.forum.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByNome(String usuario);

    Usuario findByNome(String usuario);
}
