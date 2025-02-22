package com.msoft.mek.dto.tutor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DTOTutorDetails {

	private Long tutorId;

	@NotBlank(message = "Nome obrigatório")
	private String tutorName;

	@NotNull(message = "Idade obrigatória")
	private Integer idade;

	@NotNull(message = "Quantidade de anos de experiência obrigatória")
	private Integer anosExperiencia;

	public DTOTutorDetails(Long tutorId, @NotBlank(message = "Nome obrigatório") String tutorName,
			@NotNull(message = "Idade obrigatória") Integer idade,
			@NotNull(message = "Quantidade de anos de experiência obrigatória") Integer anosExperiencia) {
		this.tutorId = tutorId;
		this.tutorName = tutorName;
		this.idade = idade;
		this.anosExperiencia = anosExperiencia;
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
	
} // DTOTutorDetails