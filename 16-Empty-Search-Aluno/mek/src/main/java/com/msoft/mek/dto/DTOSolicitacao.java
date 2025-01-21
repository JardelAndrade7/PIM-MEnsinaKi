package com.msoft.mek.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DTOSolicitacao {

    private Long solicitacaoId;

	@NotBlank(message = "Título obrigatório")
	private String title;

	@NotBlank(message = "Modelo obrigatório")
	private String modelo;

	@NotBlank(message = "Local obrigatório")
	private String local;
    
    @NotNull(message = "Área é obrigatória")
	private Long areaId;

    public DTOSolicitacao(@NotBlank(message = "Título obrigatório") String title,
            @NotBlank(message = "Modelo obrigatório") String modelo,
            @NotBlank(message = "Ambiente obrigatório") String local,
            @NotNull(message = "Área é obrigatória") Long areaId) {
        this.title = title;
        this.modelo = modelo;
        this.local = local;
        this.areaId = areaId;
    }

    public Long getSolicitacaoId() {
        return solicitacaoId;
    }
    public void setSolicitacaoId(Long solicitacaoId) {
        this.solicitacaoId = solicitacaoId;
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

    public Long getAreaId() {
        return areaId;
    }
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }
} // DTOSolicitacao