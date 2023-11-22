package com.br.alura.forum.domain.topico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico status,
        Long usuario,
        Long curso
) {
    public DadosDetalhamentoTopico(Topico topico){
        this(
                topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(),
                topico.getStatus(), topico.getCurso().getId(), topico.getCurso().getId()
        );
    }
}
