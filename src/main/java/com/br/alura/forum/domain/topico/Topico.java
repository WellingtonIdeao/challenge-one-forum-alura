package com.br.alura.forum.domain.topico;

import com.br.alura.forum.domain.curso.Curso;
import com.br.alura.forum.domain.resposta.Resposta;
import com.br.alura.forum.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Topico")
@Table( name = "topico")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Topico {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String mensagem;

	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusTopico status = StatusTopico.NAO_RESPONDIDO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "curso_id")
	private Curso curso;

	@OneToMany(mappedBy = "topico")
	private List<Resposta> respostas = new ArrayList<>();

	private Boolean ativo;

	public Topico(DadosCadastroTopico dados, Usuario usuario, Curso curso) {
		this.titulo = dados.titulo();
		this.mensagem = dados.mensagem();
		this.usuario = usuario;
		this.curso = curso;
		this.ativo = true;
	}

	public void atualizarInformacoes(DadosAtualizacaoTopico dados) {
		if(dados.titulo() != null) {
			this.titulo = dados.titulo();
		}
		if (dados.mensagem() != null){
			this.mensagem = dados.mensagem();
		}
		if(dados.status() != null) {
			this.status = dados.status();
		}
	}

	public void atualizarCurso(Curso curso) {
		if (curso != null) {
			this.curso = curso;
		}
	}

	public void inativar() {
		this.ativo = false;
	}
}
