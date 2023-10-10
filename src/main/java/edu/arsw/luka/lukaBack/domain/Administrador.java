package edu.arsw.luka.lukaBack.domain;

import lombok.NonNull;

public class Administrador extends Usuario{

    public Administrador(@NonNull String nombre, @NonNull String nombreUsuario, @NonNull String correo,
            @NonNull TipoDocumento tipoDocumento, @NonNull String numDocumento, @NonNull String contrasena) {
        super(nombre, nombreUsuario, correo, tipoDocumento, numDocumento, contrasena);
    }
    
}
