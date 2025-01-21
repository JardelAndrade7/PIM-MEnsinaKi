package com.msoft.mek.dto.tutor;

import jakarta.validation.constraints.NotBlank;

public class DTOTutorPassword {

	private Long tutorId;
	private String tutorName;
	private Long areaAptidao;
	private Integer idade;
	private Integer anosExperiencia;
	private String tutorEmail;

	@NotBlank(message = "A Senha é obrigatória e deve conter entre 8 e 16 caracteres")
	private String passw;

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

	public String getPassw() {
		return passw;
	}

	public void setPassw(String password) {
		this.passw = password;
	}
	
} // DTOTutorPassword