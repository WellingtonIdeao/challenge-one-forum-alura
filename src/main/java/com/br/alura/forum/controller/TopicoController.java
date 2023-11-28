package com.br.alura.forum.controller;

import com.br.alura.forum.domain.ValidacaoException;
import com.br.alura.forum.domain.curso.CursoRepository;
import com.br.alura.forum.domain.topico.*;
import com.br.alura.forum.domain.topico.validacoes.cadastro.ValidadorCadastroTopico;
import com.br.alura.forum.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    List<ValidadorCadastroTopico> validadoresCadastro;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuild) {
        this.validadoresCadastro.forEach(v -> v.validar(dados));

        if(!usuarioRepository.existsByNome(dados.usuario())){
            throw new ValidacaoException("Usuário não cadastrado");
        }
        if(!cursoRepository.existsByNome(dados.curso())){
            throw new ValidacaoException("Curso não cadastrado");
        }
        var usuario = this.usuarioRepository.findByNome(dados.usuario());
        var curso = this.cursoRepository.findByNome(dados.curso());
        var topico = new Topico(dados, usuario, curso);

        this.topicoRepository.save(topico);

        var uri = uriBuild.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }
    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(@RequestParam(required = false) String curso ,
                                                            @RequestParam(required = false) Integer ano,
                                                            @PageableDefault(size= 10, sort = {"dataCriacao"}, direction = Sort.Direction.ASC) Pageable paginacao){
       var page = this.topicoRepository.findAllWithFilters(curso, ano, paginacao).map(DadosListagemTopico::new);
       return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        if(!topicoRepository.existsById(id)){
            throw new ValidacaoException("Tópico não encontrado");
        }
        var topico = this.topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody DadosAtualizacaoTopico dados){
        if(!topicoRepository.existsById(id)){
            throw new ValidacaoException("Tópico não encontrado");
        }
        var topico = this.topicoRepository.getReferenceById(id);

        if(dados.curso() != null){
            var curso = this.cursoRepository.findByNome(dados.curso());
            if(curso == null){
                throw new ValidacaoException("Curso informado não existe, informe um curso válido!");
            }
            topico.atualizarCurso(curso);
        }
        topico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity remover(@PathVariable Long id){
        var topico = this.topicoRepository.getReferenceById(id);
        topico.inativar();
        return ResponseEntity.noContent().build();
    }
}
