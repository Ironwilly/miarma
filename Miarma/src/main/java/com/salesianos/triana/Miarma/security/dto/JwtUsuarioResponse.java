package com.salesianos.triana.Miarma.security.dto;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtUsuarioResponse {

    private String email;

    private String nick;

    private String nombre;

    private LocalDate fechNac;

    private String apellidos;

    private String avatar;

    private String token;
}
