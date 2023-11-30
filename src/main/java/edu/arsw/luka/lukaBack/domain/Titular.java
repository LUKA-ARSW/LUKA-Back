package edu.arsw.luka.lukaBack.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Titular extends Usuario{

    @NonNull
    private CuentaBancaria cuentaBancaria;

    public Titular(@NonNull String nombre, @NonNull String nombreUsuario, @NonNull String correo,
            @NonNull TipoDocumento tipoDocumento, @NonNull String numDocumento, @NonNull String contrasena, 
            @NonNull CuentaBancaria cuentaBancaria, @NonNull Rol rol) {
        super(nombre, nombreUsuario, correo, tipoDocumento, numDocumento, contrasena, rol);
        this.cuentaBancaria = cuentaBancaria;
    }

    
}
