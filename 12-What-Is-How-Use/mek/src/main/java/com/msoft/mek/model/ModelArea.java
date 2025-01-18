package com.msoft.mek.model;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
public class ModelArea implements Serializable {

	private static final Long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long areaId;
	private String areaName;
	private String description;
	
	public ModelArea() {
	}

	public ModelArea(String areaName, String description) {
		this.areaName = areaName;
		this.description = description;
	}

	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
} // ModelArea