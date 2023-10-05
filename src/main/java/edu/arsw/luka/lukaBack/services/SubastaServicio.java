package edu.arsw.luka.lukaBack.services;


import java.time.LocalDateTime;

import edu.arsw.luka.lukaBack.domain.Subasta;
import edu.arsw.luka.lukaBack.exception.LukaException;

public interface SubastaServicio {

    Subasta agregarSubasta(Subasta subasta) throws LukaException;

    Subasta modificarFechaSubasta(String nombre,LocalDateTime fechaInicio, LocalDateTime fechaFin) throws LukaException;
    
}
