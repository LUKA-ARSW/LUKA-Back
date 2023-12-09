package edu.arsw.luka.lukaBack.domain;

import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
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

    @NonNull
    private Rol rol;
    
}
