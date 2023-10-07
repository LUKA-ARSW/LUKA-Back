package edu.arsw.luka.lukaBack.persistence.repositorio;

import java.util.List;
import java.time.LocalDateTime;

import edu.arsw.luka.lukaBack.domain.Subasta;
import edu.arsw.luka.lukaBack.exception.LukaException;

public interface SubastaRepositorio {
    
    Subasta agregarSubasta(Subasta subasta) throws LukaException;

    Subasta modificarFechaSubasta(String nombre,LocalDateTime fechaInicio, LocalDateTime fechaFin) throws LukaException;

    List<Subasta> consultarTodasLasSubastas();
    
}
