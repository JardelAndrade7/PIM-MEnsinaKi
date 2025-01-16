package com.msoft.mek.model;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
public class ModelTutor implements Serializable {

	private static final Long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long tutorId;

	private String tutorName;

	@ManyToOne
	private ModelArea areaFormacao;

	@ManyToOne
	private ModelArea areaAptidao;

	private Integer idade;
	private Integer anosExperiencia;
	private String tutorEmail;
	private String passw;
	private String token;

	public ModelTutor() {
	}

	public ModelTutor(String tutorName, ModelArea areaFormacao, ModelArea areaAptidao, Integer idade,
			Integer anosExperiencia, String tutorEmail, String passw) {
		this.tutorName = tutorName;
		this.areaFormacao = areaFormacao;
		this.areaAptidao = areaAptidao;
		this.idade = idade;
		this.anosExperiencia = anosExperiencia;
		this.tutorEmail = tutorEmail;
		this.passw = passw;
	}

	public long getTutorId() {
		return tutorId;
	}

	public void setTutorId(Long tutorId) {
		this.tutorId = tutorId;
	}

	public String getTutorName() {
		return tutorName;
	}
	public void setTutorName(String tutorName) {
		this.tutorName = tutorName;
	}

	public ModelArea getAreaFormacao() {
		return areaFormacao;
	}

	public void setAreaFormacao(ModelArea areaFormacao) {
		this.areaFormacao = areaFormacao;
	}

	public ModelArea getAreaAptidao() {
		return areaAptidao;
	}

	public void setAreaAptidao(ModelArea areaAptidao) {
		this.areaAptidao = areaAptidao;
	}

	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Integer getAnosExperiencia() {
		return anosExperiencia;
	}
	public void setAnosExperiencia(Integer anosExperiencia) {
		this.anosExperiencia = anosExperiencia;
	}

	public String getTutorEmail() {
		return tutorEmail;
	}

	public void setTutorEmail(String tutorEmail) {
		this.tutorEmail = tutorEmail;
	}

	public String getPassw() {
		return passw;
	}

	public void setPassw(String password) {
		this.passw = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
} // ModelTutor