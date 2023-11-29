package com.br.alura.forum.domain.topico;

import com.br.alura.forum.domain.ValidacaoException;
import com.br.alura.forum.domain.curso.Curso;
import com.br.alura.forum.domain.curso.CursoRepository;
import com.br.alura.forum.domain.topico.validacoes.cadastro.ValidadorCadastroTopico;
import com.br.alura.forum.domain.usuario.Usuario;
import com.br.alura.forum.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    List<ValidadorCadastroTopico> validadoresCadastro;


    public Topico cadastrar(DadosCadastroTopico dados){
        this.validadoresCadastro.forEach(v -> v.validar(dados));

        if(!usuarioRepository.existsByNome(dados.usuario())){
            throw new ValidacaoException("Usuário não cadastrado");
        }
        if(!cursoRepository.existsByNome(dados.curso())){
            throw new ValidacaoException("Curso não cadastrado");
        }
        var usuario = getUsuario(dados.usuario());
        var curso = getCurso(dados.curso());
        var topico = new Topico(dados, usuario, curso);

        this.topicoRepository.save(topico);
        return topico;
    }

    public Page<DadosListagemTopico> listar(String curso, Integer ano, Pageable paginacao) {
        return this.topicoRepository.findAllWithFilters(curso, ano, paginacao).map(DadosListagemTopico::new);
    }
    public Topico detalhar(Long id){
        if(!topicoRepository.existsByIdAndAtivoTrue(id)){
            throw new ValidacaoException("Tópico não encontrado");
        }
        return this.topicoRepository.getReferenceById(id);
    }

    public Topico atualizar(Long id, DadosAtualizacaoTopico dados){
        if(!topicoRepository.existsByIdAndAtivoTrue(id)){
            throw new ValidacaoException("Tópico não encontrado");
        }
        var topico = this.topicoRepository.getReferenceById(id);

        if(dados.curso() != null){
            var curso = getCurso(dados.curso());
            if(curso == null){
                throw new ValidacaoException("Curso informado não existe, informe um curso válido!");
            }
            topico.atualizarCurso(curso);
        }
        topico.atualizarInformacoes(dados);
        return topico;
    }

    public void remover(Long id){
        var topico = this.topicoRepository.getReferenceById(id);
        topico.inativar();
    }

    private Curso getCurso(String nome){
        return this.cursoRepository.findByNome(nome);
    }

    private Usuario getUsuario(String nome) {
        return this.usuarioRepository.findByNome(nome);
    }
}
