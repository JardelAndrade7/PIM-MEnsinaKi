package com.msoft.mek.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class DTOUpdateEmailAluno {

	private Long alunoId;

	@NotBlank(message = "O E-Mail é obrigatório")
	@Email(message = "Digite um E-Mail válido")
	private String alunoEmail;

	public Long getAlunoId() {
		return alunoId;
	}

	public void setAlunoId(Long codigo) {
		this.alunoId = codigo;
	}

	public String getAlunoEmail() {
		return alunoEmail;
	}

	public void setAlunoEmail(String alunoEmail) {
		this.alunoEmail = alunoEmail;
	}
} // DTOUpdateEmailAluno