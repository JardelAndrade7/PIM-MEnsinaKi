package com.msoft.mek.dto.tutor;

import jakarta.validation.constraints.NotBlank;

public class DTOUpdatePasswTutor {

	private Long tutorId;
	
	@NotBlank(message = "A Senha é obrigatória e deve conter entre 8 e 16 caracteres")
	private String passw;

	public long getTutorId() {
		return tutorId;
	}

	public void setTutorId(Long tutorId) {
		this.tutorId = tutorId;
	}

	public String getPassw() {
		return passw;
	}

	public void setPassw(String password) {
		this.passw = password;
	}
	
} // DTOUpdatePasswTutor