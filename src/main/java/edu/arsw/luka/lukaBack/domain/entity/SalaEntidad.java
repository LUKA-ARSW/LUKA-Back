package edu.arsw.luka.lukaBack.domain.entity;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.arsw.luka.lukaBack.domain.Comprador;
import edu.arsw.luka.lukaBack.domain.ElementoSubasta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sala")
public class SalaEntidad {
    
    @Id
    private String nombre; 
      
    private String subasta;    
    private Collection<String> compradores;
    private Collection<ElementoSubasta> elementoSubasta;
    
}
