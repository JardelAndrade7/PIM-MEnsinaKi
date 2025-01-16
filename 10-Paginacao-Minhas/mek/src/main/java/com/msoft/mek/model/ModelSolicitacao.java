package com.msoft.mek.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class ModelSolicitacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long solicitacaoId;
	private String title;
	private String modelo;
	private String local;
	@ManyToOne
	private ModelAluno aluno;
	@ManyToOne
	private ModelArea area;

	public ModelSolicitacao() {
	}

	public ModelSolicitacao(Long solicitacaoId, String title, String modelo, String local, ModelAluno aluno, ModelArea area) {
		this.title = title;
		this.modelo = modelo;
		this.local = local;
		this.aluno = aluno;
		this.area = area;
	}

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

	public ModelArea getArea() {
		return area;
	}
	public void setArea(ModelArea area) {
		this.area = area;
	}

} // ModelSolicitacao