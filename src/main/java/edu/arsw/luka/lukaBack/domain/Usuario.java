package edu.arsw.luka.lukaBack.domain;

import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class Usuario {
    @NonNull
    private String nombre;

    @NonNull
    private String nombreUsuario;

    @NonNull
    private String correo;

    @NonNull
    private TipoDocumento tipoDocumento;

    @NonNull
    private String numDocumento;

    @NonNull
    private String contrasena;


    
}
