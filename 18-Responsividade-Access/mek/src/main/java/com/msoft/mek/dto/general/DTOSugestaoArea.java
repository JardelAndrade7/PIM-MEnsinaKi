package com.msoft.mek.dto.general;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DTOSugestaoArea {

    private Long sugestaoId;

	@NotBlank(message = "Título obrigatório")
	private String title;

	@NotBlank(message = "Modelo obrigatório")
	private String modelo;

	@NotBlank(message = "Local obrigatório")
	private String local;

    @NotNull(message = "Preço obrigatório")
    private Double price;
    
    @NotNull(message = "Área é obrigatória")
	private Long areaId;

    public DTOSugestaoArea(@NotBlank(message = "Título obrigatório") String title,
            @NotBlank(message = "Modelo obrigatório") String modelo,
            @NotBlank(message = "Local obrigatório") String local,
            @NotNull(message = "Preço obrigatório") Double price,
            @NotNull(message = "Área é obrigatória") Long areaId) {
        this.title = title;
        this.modelo = modelo;
        this.local = local;
        this.price = price;
        this.areaId = areaId;
    }

    public Long getSugestaoId() {
        return sugestaoId;
    }
    public void setSugestaoId(Long solicitacaoId) {
        this.sugestaoId = solicitacaoId;
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

    public Long getAreaId() {
        return areaId;
    }
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }
} // DTOSugestaoArea