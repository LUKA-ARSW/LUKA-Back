package edu.arsw.luka.lukaBack.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

@Getter
@AllArgsConstructor
public enum TipoSubasta {
    CORTA(0,60),
    LARGA(60,8760);

    private int valorMin;
    private int valorMax;


    
    public boolean fechasCoinciden(LocalDateTime fechaInicio, LocalDateTime fechaFin){
       long duracion = Duration.between(fechaInicio, fechaFin).toMinutes();
       return duracion > valorMin && duracion <= valorMax;
    }

    
}
