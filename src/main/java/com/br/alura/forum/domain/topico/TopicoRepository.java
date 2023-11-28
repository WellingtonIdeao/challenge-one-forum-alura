package com.br.alura.forum.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


   @Query("""
           SELECT t FROM Topico t
           WHERE (:curso IS NULL OR t.curso.nome = :curso)
           AND (:ano IS NULL OR YEAR(t.dataCriacao) = :ano)
           AND t.ativo = true
           """)
   Page<Topico> findAllWithFilters(String curso, Integer ano, Pageable paginacao);
}
