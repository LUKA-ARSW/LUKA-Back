package edu.arsw.luka.lukaBack.domain;

import lombok.NonNull;

public class Comprador extends Titular{

    public Comprador(@NonNull String nombre, @NonNull String nombreUsuario, @NonNull String correo,
            @NonNull TipoDocumento tipoDocumento, @NonNull String numDocumento, @NonNull String contrasena,
            @NonNull CuentaBancaria cuentaBancaria) {
        super(nombre, nombreUsuario, correo, tipoDocumento, numDocumento, contrasena, cuentaBancaria);
    }
    
}

