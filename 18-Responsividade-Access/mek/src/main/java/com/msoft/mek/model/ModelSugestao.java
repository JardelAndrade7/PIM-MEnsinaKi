package com.msoft.mek.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class ModelSugestao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long sugestaoId;
	private String title;
	private String modelo;
	private String local;
	private Double price;
	@ManyToOne
	private ModelTutor tutor;
	@ManyToOne
	private ModelArea area;

	public ModelSugestao() {
	}

	public ModelSugestao(String title, String modelo, String local, Double price, ModelTutor tutor, ModelArea area) {
		this.title = title;
		this.modelo = modelo;
		this.local = local;
		this.price = price;
		this.tutor = tutor;
		this.area = area;
	}

	public Long getSugestaoId() {
		return sugestaoId;
	}
	public void setSugestaoId(Long sugestaoId) {
		this.sugestaoId = sugestaoId;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}

	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public ModelTutor getTutor () {
		return tutor;
	}
	public void setTutor(ModelTutor tutor) {
		this.tutor = tutor;
	}

	public ModelArea getArea () {
		return area;
	}
	public void setArea(ModelArea area) {
		this.area = area;
	}
} // ModelSugestao