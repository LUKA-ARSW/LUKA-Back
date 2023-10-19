package edu.arsw.luka.lukaBack.domain;

import java.util.Collection;

import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class Sala {

    @NonNull
    private String nombre;

    @NonNull
    private Subasta subasta;


    private Collection<String> compradores;

    private Collection<ElementoSubasta> elementoSubasta;

    
    public void agregarComprador(String comprador) {
        compradores.add(comprador);
    }
    
}
