package edu.arsw.luka.lukaBack.domain;

import java.util.Collection;

import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Sala {

    @NonNull
    private Subasta subasta;

    @NonNull
    private Collection<Comprador> compradores;
    
}
