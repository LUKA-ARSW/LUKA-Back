package edu.arsw.luka.lukaBack.domain;

import java.util.Collection;

import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Sala {

    @NonNull
    private String nombre;

    @NonNull
    private Subasta subasta;


    private Collection<Comprador> compradores;

    private Collection<ElementoSubasta> elementoSubasta;

    
    public void agregarCompradore(Comprador comprador) {
        compradores.add(comprador);
    }
    
}
