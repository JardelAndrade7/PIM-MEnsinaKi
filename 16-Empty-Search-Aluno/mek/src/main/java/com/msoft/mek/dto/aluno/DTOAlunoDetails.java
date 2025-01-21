package com.msoft.mek.dto.aluno;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DTOAlunoDetails {

	private Long alunoId;

	@NotBlank(message = "Nome obrigatório")
	private String alunoName;

	@NotNull(message = "Série obrigatória")
	private Long serie;

    @NotNull(message = "Área de Dificuldade obrigatória")
	private Long areaDificuldade;

    @NotNull(message = "Idade obrigatória")
	private Integer idade;

	public DTOAlunoDetails(
			Long alunoId,
			@NotBlank(message = "Nome obrigatório") String alunoName,
			@NotNull(message = "Série obrigatória") Long serie,
			@NotNull(message = "Área de Dificuldade obrigatória") Long areaDificuldade,
			@NotNull(message = "Idade obrigatória") Integer idade) {
		this.alunoId = alunoId;
		this.alunoName = alunoName;
		this.serie = serie;
		this.areaDificuldade = areaDificuldade;
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

	public @NotNull(message = "Série obrigatória") Long getSerie() {
		return serie;
	}

	public void setSerie(@NotNull(message = "Série obrigatória") Long serie) {
		this.serie = serie;
	}

	public Long getAreaDificuldade() {
		return areaDificuldade;
	}

	public void setAreaDificuldade(Long areaDificuldade) {
		this.areaDificuldade = areaDificuldade;
	}
} // DTOAlunoDetails