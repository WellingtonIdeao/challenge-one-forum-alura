package com.br.alura.forum.domain.topico.validacoes.cadastro;

import com.br.alura.forum.domain.ValidacaoException;
import com.br.alura.forum.domain.topico.DadosCadastroTopico;
import com.br.alura.forum.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTituloMensagemJaExistentes  implements ValidadorCadastroTopico{
    @Autowired
    private TopicoRepository topicoRepository;
    @Override
    public void validar(DadosCadastroTopico dados) {
        var topico =  this.topicoRepository.findByTituloAndMensagem(dados.titulo(), dados.mensagem());
        if(topico != null){
            throw new ValidacaoException("Título e mensagem já existente");
        }
    }
}
