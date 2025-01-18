package com.msoft.mek.dto;

import jakarta.validation.constraints.NotBlank;

public class DTOUpdatePasswAluno {

	private Long alunoId;

	@NotBlank(message = "A Senha é obrigatória e deve conter entre 8 e 16 caracteres")
	private String passw;

	public Long getAlunoId() {
		return alunoId;
	}

	public void setAlunoId(Long codigo) {
		this.alunoId = codigo;
	}

	public String getPassw() {
		return passw;
	}

	public void setPassw(String password) {
		this.passw = password;
	}
} // DTOUpdatePasswAluno