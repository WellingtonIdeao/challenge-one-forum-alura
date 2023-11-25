package com.br.alura.forum.domain.topico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico status,
        String autor,
        String curso
) {
    public DadosDetalhamentoTopico(Topico topico){
        this(
                topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(),
                topico.getStatus(), topico.getUsuario().getNome(), topico.getCurso().getNome()
        );
    }
}
