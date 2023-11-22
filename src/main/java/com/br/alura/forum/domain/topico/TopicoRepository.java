package com.br.alura.forum.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
   @Query("""
           SELECT t FROM Topico t
           WHERE t.titulo = :titulo
           AND t.mensagem = :mensagem
           ORDER BY t.titulo
           LIMIT 1""")
    Topico findByTituloAndMensagem(String titulo, String mensagem);
}
