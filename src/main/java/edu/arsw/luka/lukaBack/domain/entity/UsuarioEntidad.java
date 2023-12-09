package edu.arsw.luka.lukaBack.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.arsw.luka.lukaBack.domain.CuentaBancaria;
import edu.arsw.luka.lukaBack.domain.Rol;
import edu.arsw.luka.lukaBack.domain.TipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class UsuarioEntidad {

    private String nombre;
    private String nombreUsuario;

    @Id
    private String correo;
    
    private TipoDocumento tipoDocumento;
    private String numDocumento;
    private String contrasena;
    private Rol rol;
    private CuentaBancaria cuentaBancaria;
    
}
