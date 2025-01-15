package com.msoft.mek.model;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class ModelAluno implements Serializable {

	private static final Long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long alunoId;
	@NotBlank(message = "Nome obrigatório")
	private String alunoName;
	@NotNull(message = "Idade obrigatória")
	private Integer idade;
	@NotBlank(message = "Série obrigatória")
	private String serie;
	@NotBlank(message = "O E-Mail é obrigatório")
	@Email(message = "Digite um E-Mail válido")
	private String alunoEmail;
	@NotBlank(message = "A Senha é obrigatória e deve conter entre 8 e 16 caracteres")
	private String passw;
	private String token;

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

	public @NotBlank(message = "Série obrigatória") String getSerie() {
		return serie;
	}

	public void setSerie(@NotBlank(message = "Série obrigatória") String serie) {
		this.serie = serie;
	}

	public String getAlunoEmail() {
		return alunoEmail;
	}

	public void setAlunoEmail(String alunoEmail) {
		this.alunoEmail = alunoEmail;
	}

	public String getPassw() {
		return passw;
	}

	public void setPassw(String password) {
		this.passw = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
} // ModelAluno