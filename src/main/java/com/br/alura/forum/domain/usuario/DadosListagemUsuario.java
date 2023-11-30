package com.br.alura.forum.domain.usuario;

public record DadosListagemUsuario(Long id, String nome,String sobrenome, String email ) {
    public DadosListagemUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getSobreNome(), usuario.getEmail());
    }
}
