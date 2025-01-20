package com.msoft.mek.dto.tutor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class DTOTutorEmail {

	private Long tutorId;
	private String tutorName;
	private Long areaAptidao;
	private Integer idade;
	private Integer anosExperiencia;

	@NotBlank(message = "O E-Mail é obrigatório")
	@Email(message = "Digite um E-Mail válido")
	private String tutorEmail;

	public DTOTutorEmail(Long tutorId, String tutorName, Long areaAptidao, Integer idade, Integer anosExperiencia,
			@NotBlank(message = "O E-Mail é obrigatório") @Email(message = "Digite um E-Mail válido") String tutorEmail) {
		this.tutorId = tutorId;
		this.tutorName = tutorName;
		this.areaAptidao = areaAptidao;
		this.idade = idade;
		this.anosExperiencia = anosExperiencia;
		this.tutorEmail = tutorEmail;
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

	public Long getAreaAptidao() {
		return areaAptidao;
	}

	public void setAreaAptidao(Long areaAptidao) {
		this.areaAptidao = areaAptidao;
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

	public String getTutorEmail() {
		return tutorEmail;
	}

	public void setTutorEmail(String tutorEmail) {
		this.tutorEmail = tutorEmail;
	}
	
} // DTOTutorEmail