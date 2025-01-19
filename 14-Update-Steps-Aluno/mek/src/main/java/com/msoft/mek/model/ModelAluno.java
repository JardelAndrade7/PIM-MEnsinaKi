package com.msoft.mek.model;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
public class ModelAluno implements Serializable {

	private static final Long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long alunoId;

	private String alunoName;
	private ModelSerie serie;

	@ManyToOne
	private ModelArea areaDificuldade;

	private Integer idade;
	private String alunoEmail;
	private String passw;
	private String token;

	public ModelAluno () {
	}

	public ModelAluno(String alunoName, ModelSerie serie,
	ModelArea areaDificuldade, Integer idade, String alunoEmail, String passw) {
		this.alunoName = alunoName;
		this.serie = serie;
		this.areaDificuldade = areaDificuldade;
		this.idade = idade;
		this.alunoEmail = alunoEmail;
		this.passw = passw;
	}

	public Long getAlunoId() {
		return alunoId;
	}

	public void setAlunoId(Long codigo) {
		this.alunoId = codigo;
	}

	public String getAlunoName() {
		return alunoName;
	}
	public void setAlunoName(String nome) {
		this.alunoName = nome;
	}
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public ModelSerie getSerie() {
		return serie;
	}

	public void setSerie(ModelSerie serie) {
		this.serie = serie;
	}

	public String getAlunoEmail() {
		return alunoEmail;
	}

	public void setAlunoEmail(String alunoEmail) {
		this.alunoEmail = alunoEmail;
	}

	public String getPassw() {
		return passw;
	}

	public void setPassw(String password) {
		this.passw = password;
	}

	public ModelArea getAreaDificuldade() {
		return areaDificuldade;
	}

	public void setAreaDificuldade(ModelArea areaDificuldade) {
		this.areaDificuldade = areaDificuldade;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
} // ModelAluno