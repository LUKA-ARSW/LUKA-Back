package edu.arsw.luka.lukaBack.persistence.repositorio;

import java.util.List;

import edu.arsw.luka.lukaBack.domain.Sala;
import edu.arsw.luka.lukaBack.exception.LukaException;

public interface SalaRepositorio {
    Sala agregarSala(Sala sala) throws LukaException;
    List<Sala> consultarTodasLasSalas();
    Sala consultarSalaPorNombre(String nombre) throws LukaException;
    void eliminarSala(String nombre) throws LukaException;
    
}
