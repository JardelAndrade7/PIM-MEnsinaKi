package com.msoft.mek.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ModelSerie implements Serializable {

    private static final Long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long serieId;
	private String serieName;
    
    public Long getSerieId() {
        return serieId;
    }
    public void setSerieId(Long serieId) {
        this.serieId = serieId;
    }

    public String getSerieName() {
        return serieName;
    }

    public void setSerieName(String serieName) {
        this.serieName = serieName;
    }
    
} // ModelSerie
