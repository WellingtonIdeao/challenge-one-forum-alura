package com.br.alura.forum.controller;

import com.br.alura.forum.domain.curso.CursoRepository;
import com.br.alura.forum.domain.topico.*;
import com.br.alura.forum.domain.topico.validacoes.cadastro.ValidadorCadastroTopico;
import com.br.alura.forum.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

        var usuario = this.usuarioRepository.getReferenceById(dados.usuario());
        var curso = this.cursoRepository.getReferenceById(dados.curso());
        var topico = new Topico(dados, usuario, curso);

        this.topicoRepository.save(topico);

        var uri = uriBuild.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }
    @GetMapping
    public List<DadosListagemTopico> listar(){
        return this.topicoRepository.findAll().stream().map(DadosListagemTopico::new).toList();
    }
}
