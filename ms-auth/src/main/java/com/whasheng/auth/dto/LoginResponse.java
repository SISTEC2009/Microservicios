package com.whasheng.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private boolean exito;
    private String mensaje;
    private String token;
    private String username;
    private String rol;

    // Constructor de conveniencia para una respuesta de error (sin token)
    public LoginResponse(boolean exito, String mensaje) {
        this.exito = exito;
        this.mensaje = mensaje;
    }
}