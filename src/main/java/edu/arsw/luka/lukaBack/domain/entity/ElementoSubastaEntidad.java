package edu.arsw.luka.lukaBack.domain.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.data.util.Pair;

import edu.arsw.luka.lukaBack.domain.Comprador;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElementoSubastaEntidad {

    private String idProducto;

    private Double pujaMaxima;

    private Collection<Pair<Comprador,Double>> compradores;


    
}
