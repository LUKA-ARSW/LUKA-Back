package edu.arsw.luka.lukaBack.persistence.repositorio.mongoRepositorio;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import edu.arsw.luka.lukaBack.domain.Subasta;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.persistence.repositorio.SubastaRepositorio;

@Repository
public class MongoSubastaRepositorio implements SubastaRepositorio{

    @Override
    public Subasta agregarSubasta(Subasta subasta) throws LukaException{
        return null;
    }

    @Override
    public Subasta modificarFechaSubasta(String nombre,LocalDateTime fechaInicio, LocalDateTime fechaFin) throws LukaException{
        return null;
    }


    
}
