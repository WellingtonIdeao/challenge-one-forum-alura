package com.br.alura.forum.domain.topico;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DadosListagemTopico(

        Long id,
        String titulo,
        String messagem,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataCriacao,
        StatusTopico status,
        String autor,
        String curso


) {
    public DadosListagemTopico(Topico topico){
        this(topico.getId(),topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(),
                topico.getStatus(),topico.getUsuario().getNome(), topico.getCurso().getNome());
    }
}
