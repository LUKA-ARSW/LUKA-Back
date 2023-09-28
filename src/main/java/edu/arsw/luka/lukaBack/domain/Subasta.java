package edu.arsw.luka.lukaBack.domain;

import java.util.Date;

import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Subasta {

    @NonNull
    private final String nombre;

    private Date fechaInicio;
    private Date fechaFin;

    @NonNull
    private Estado estado;

    
}
