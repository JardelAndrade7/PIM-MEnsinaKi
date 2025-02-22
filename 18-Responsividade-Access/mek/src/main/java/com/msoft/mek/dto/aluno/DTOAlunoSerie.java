package com.msoft.mek.dto.aluno;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DTOAlunoSerie {

	private Long alunoId;

	@NotBlank(message = "Nome obrigatório")
	private String alunoName;

	@NotNull(message = "Idade obrigatória")
	private Integer idade;

	@NotNull(message = "Série obrigatória")
	private Long serie;

	public DTOAlunoSerie(
			Long alunoId,
			@NotBlank(message = "Nome obrigatório") String alunoName,
			@NotNull(message = "Idade obrigatória") Integer idade,
			@NotNull(message = "Série obrigatória") Long serie) {
		this.alunoId = alunoId;
		this.alunoName = alunoName;
		this.idade = idade;
		this.serie = serie;
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

	public @NotNull(message = "Série obrigatória") Long getSerie() {
		return serie;
	}

	public void setSerie(@NotNull(message = "Série obrigatória") Long serie) {
		this.serie = serie;
	}
} // DTOAlunoSerie