package com.msoft.mek.dto.aluno;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class DTOAlunoEmail {

	private Long alunoId;
	private String alunoName;
	private Long serie;
	private Long areaDificuldade;
	private Integer idade;

	@NotBlank(message = "O E-Mail é obrigatório")
	@Email(message = "Digite um E-Mail válido")
	private String alunoEmail;

	public DTOAlunoEmail(Long alunoId, String alunoName, Long serie, Long areaDificuldade, Integer idade,
			@NotBlank(message = "O E-Mail é obrigatório") @Email(message = "Digite um E-Mail válido") String alunoEmail) {
		this.alunoId = alunoId;
		this.alunoName = alunoName;
		this.serie = serie;
		this.areaDificuldade = areaDificuldade;
		this.idade = idade;
		this.alunoEmail = alunoEmail;
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

	public void setAlunoName(String alunoName) {
		this.alunoName = alunoName;
	}

	public Long getSerie() {
		return serie;
	}

	public void setSerie(Long serie) {
		this.serie = serie;
	}

	public Long getAreaDificuldade() {
		return areaDificuldade;
	}

	public void setAreaDificuldade(Long areaDificuldade) {
		this.areaDificuldade = areaDificuldade;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getAlunoEmail() {
		return alunoEmail;
	}

	public void setAlunoEmail(String alunoEmail) {
		this.alunoEmail = alunoEmail;
	}

} // DTOAlunoEmail