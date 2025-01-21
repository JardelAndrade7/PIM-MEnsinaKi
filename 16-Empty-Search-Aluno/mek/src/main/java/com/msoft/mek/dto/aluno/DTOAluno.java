package com.msoft.mek.dto.aluno;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DTOAluno {

	private Long alunoId;

	@NotBlank(message = "Nome obrigatório")
	private String alunoName;

	@NotNull(message = "Série obrigatória")
	private Long serie;

    @NotNull(message = "Área de Dificuldade obrigatória")
	private Long areaDificuldade;

    @NotNull(message = "Idade obrigatória")
	private Integer idade;

	@NotBlank(message = "O E-Mail é obrigatório")
	@Email(message = "Digite um E-Mail válido")
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
	public void setAlunoName(String nome) {
		this.alunoName = nome;
	}
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public @NotNull(message = "Série obrigatória") Long getSerie() {
		return serie;
	}

	public void setSerie(@NotNull(message = "Série obrigatória") Long serie) {
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

	public Long getAreaDificuldade() {
		return areaDificuldade;
	}

	public void setAreaDificuldade(Long areaDificuldade) {
		this.areaDificuldade = areaDificuldade;
	}
} // DTOAluno