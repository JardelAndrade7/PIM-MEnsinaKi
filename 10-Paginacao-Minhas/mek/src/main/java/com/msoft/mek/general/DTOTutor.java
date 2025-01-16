package com.msoft.mek.general;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DTOTutor {

	private Long tutorId;

	@NotBlank(message = "Nome obrigatório")
	private String tutorName;

	@ManyToOne
	@NotNull(message = "Área de Formação obrigatória")
	private Long areaFormacao;

	@ManyToOne
	@NotNull(message = "Área de Aptidão obrigatória")
	private Long areaAptidao;

	@NotNull(message = "Idade obrigatória")
	private Integer idade;

	@NotNull(message = "Quantidade de anos de experiência obrigatória")
	private Integer anosExperiencia;

	@NotBlank(message = "O E-Mail é obrigatório")
	@Email(message = "Digite um E-Mail válido")
	private String tutorEmail;
	
	@NotBlank(message = "A Senha é obrigatória e deve conter entre 8 e 16 caracteres")
	private String passw;

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

	public Long getAreaFormacao() {
		return areaFormacao;
	}

	public void setAreaFormacao(Long areaFormacao) {
		this.areaFormacao = areaFormacao;
	}

	public Long getAreaAptidao() {
		return areaAptidao;
	}

	public void setAreaAptidao(Long areaAptidao) {
		this.areaAptidao = areaAptidao;
	}
	
} // DTOTutor