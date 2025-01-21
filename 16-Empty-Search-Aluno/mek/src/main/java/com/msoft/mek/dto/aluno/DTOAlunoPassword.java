package com.msoft.mek.dto.aluno;

import jakarta.validation.constraints.NotBlank;

public class DTOAlunoPassword {

	private Long alunoId;
	private String alunoName;
	private Long serie;
	private Long areaDificuldade;
	private Integer idade;
	private String alunoEmail;

	@NotBlank(message = "A Senha é obrigatória e deve conter entre 8 e 16 caracteres")
	private String passw;

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

	public String getPassw() {
		return passw;
	}

	public void setPassw(String password) {
		this.passw = password;
	}
} // DTOAlunoPassword