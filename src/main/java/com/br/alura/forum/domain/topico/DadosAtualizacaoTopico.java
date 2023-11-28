package com.br.alura.forum.domain.topico;

public record DadosAtualizacaoTopico(
       String titulo,
       String mensagem,
       StatusTopico status,
       String curso
) {
}
