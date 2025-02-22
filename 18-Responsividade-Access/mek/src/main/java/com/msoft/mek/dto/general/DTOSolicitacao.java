package com.msoft.mek.dto.general;

import jakarta.validation.constraints.NotBlank;

public class DTOSolicitacao {

    private Long solicitacaoId;

	@NotBlank(message = "Título obrigatório")
	private String title;

	@NotBlank(message = "Modelo obrigatório")
	private String modelo;

	@NotBlank(message = "Local obrigatório")
	private String local;

    public DTOSolicitacao(@NotBlank(message = "Título obrigatório") String title,
            @NotBlank(message = "Modelo obrigatório") String modelo,
            @NotBlank(message = "Ambiente obrigatório") String local) {
        this.title = title;
        this.modelo = modelo;
        this.local = local;
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
} // DTOSolicitacao