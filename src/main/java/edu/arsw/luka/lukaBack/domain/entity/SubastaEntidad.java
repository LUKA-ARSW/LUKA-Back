package edu.arsw.luka.lukaBack.domain.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.arsw.luka.lukaBack.domain.Estado;
import edu.arsw.luka.lukaBack.domain.TipoSubasta;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "subastas")
public class SubastaEntidad {
    @Id
    private String nombre;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private TipoSubasta tipoSubasta;
    private Estado estado;
    private Collection<ProductoEntidad> productos;
    


    
}
