package com.br.alura.forum.domain.topico.validacoes.cadastro;

import com.br.alura.forum.domain.topico.DadosCadastroTopico;

public interface ValidadorCadastroTopico {
    void validar(DadosCadastroTopico dados);
}
