package com.msoft.mek.dto.aluno;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DTOAlunoDetails {

	private Long alunoId;

	@NotBlank(message = "Nome obrigatório")
	private String alunoName;

	@NotNull(message = "Idade obrigatória")
	private Integer idade;

	public DTOAlunoDetails(
			Long alunoId,
			@NotBlank(message = "Nome obrigatório") String alunoName,
			@NotNull(message = "Idade obrigatória") Integer idade) {
		this.alunoId = alunoId;
		this.alunoName = alunoName;
		this.idade = idade;
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
} // DTOAlunoDetails