package com.msoft.mek.general;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class DTOLogin {

    @NotBlank(message = "O E-Mail é obrigatório")
    @Email(message = "Digite um E-Mail válido")
    private String loginEmail;

    @NotBlank(message = "A Senha é obrigatória")
    private String passw;

    public @NotBlank(message = "A Senha é obrigatória") String getPassw() {
        return passw;
    }

    public void setPassw(@NotBlank(message = "A Senha é obrigatória") String passw) {
        this.passw = passw;
    }

    public @NotBlank(message = "O E-Mail é obrigatório") @Email(message = "Digite um E-Mail válido") String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(@NotBlank(message = "O E-Mail é obrigatório") @Email(message = "Digite um E-Mail válido") String loginEmail) {
        this.loginEmail = loginEmail;
    }
}