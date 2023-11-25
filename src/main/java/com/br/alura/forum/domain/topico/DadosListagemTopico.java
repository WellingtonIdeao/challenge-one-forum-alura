package com.br.alura.forum.domain.topico;

import com.br.alura.forum.domain.curso.Curso;

import java.time.LocalDateTime;

public record DadosListagemTopico(
        String titulo,
        String messagem,
        LocalDateTime dataCriacao,
        StatusTopico status,
        String autor,
        String curso


) {
    public DadosListagemTopico(Topico topico){
        this(topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(),
                topico.getStatus(),topico.getUsuario().getNome(), topico.getCurso().getNome());
    }
}
