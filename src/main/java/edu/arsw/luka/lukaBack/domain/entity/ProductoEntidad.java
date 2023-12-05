package edu.arsw.luka.lukaBack.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.arsw.luka.lukaBack.domain.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "productos")
public class ProductoEntidad {

    @Id
    private String idProducto;  
     
    private String nombre;
    private String descripcion;   
    private String foto;
    private Double precio;    
    private Categoria categoria;
    private String vendedor;
}
