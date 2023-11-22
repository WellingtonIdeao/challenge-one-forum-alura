package com.br.alura.forum.domain.topico;

public record DadosListagemTopico(
        Long id,
        String titulo,
        String messagem,
        StatusTopico status
) {
    public DadosListagemTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getStatus());
    }
}
