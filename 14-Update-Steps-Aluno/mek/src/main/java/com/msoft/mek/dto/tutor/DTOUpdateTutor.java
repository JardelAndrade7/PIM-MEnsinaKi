package com.msoft.mek.dto.tutor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DTOUpdateTutor {

	private Long tutorId;

	@NotBlank(message = "Nome obrigatório")
	private String tutorName;

	@ManyToOne
	@NotNull(message = "Área de Aptidão obrigatória")
	private Long areaAptidao;

	@NotNull(message = "Idade obrigatória")
	private Integer idade;

	@NotNull(message = "Quantidade de anos de experiência obrigatória")
	private Integer anosExperiencia;

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

	public Long getAreaAptidao() {
		return areaAptidao;
	}

	public void setAreaAptidao(Long areaAptidao) {
		this.areaAptidao = areaAptidao;
	}
	
} // DTOUpdateTutor