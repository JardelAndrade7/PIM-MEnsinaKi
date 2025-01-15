package com.msoft.mek.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
public class ModelSolicitacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long solicitacaoId;
	@NotBlank(message = "Título obrigatório")
	private String title;
	@NotBlank(message = "Modelo obrigatório")
	private String modelo;
	@NotBlank(message = "Ambiente obrigatório")
	private String local;
	@ManyToOne
	private ModelAluno aluno;

	public Long getSolicitacaoId() {
		return solicitacaoId;
	}
	public void setSolicitacaoId(Long solicitacaoId) {
		this.solicitacaoId = solicitacaoId;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}

	public ModelAluno getAluno() {
		return aluno;
	}
	public void setAluno(ModelAluno aluno) {
		this.aluno = aluno;
	}
} // ModelSolicitacao