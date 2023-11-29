package com.br.alura.forum.controller;


import com.br.alura.forum.domain.topico.*;
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

@RestController
@RequestMapping("topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuild) {
        var topico = this.topicoService.cadastrar(dados);
        var uri = uriBuild.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }
    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(@RequestParam(required = false) String curso ,
                                                            @RequestParam(required = false) Integer ano,
                                                            @PageableDefault(size= 10, sort = {"dataCriacao"}, direction = Sort.Direction.ASC) Pageable paginacao){
       var page = this.topicoService.listar(curso, ano, paginacao);
       return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var topico = this.topicoService.detalhar(id);

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody DadosAtualizacaoTopico dados){
        var topico = this.topicoService.atualizar(id, dados);

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity remover(@PathVariable Long id){
        this.topicoService.remover(id);

        return ResponseEntity.noContent().build();
    }
}
