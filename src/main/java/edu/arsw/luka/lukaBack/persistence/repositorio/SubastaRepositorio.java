package edu.arsw.luka.lukaBack.persistence.repositorio;

import java.util.List;
import java.time.LocalDateTime;

import edu.arsw.luka.lukaBack.domain.Producto;
import edu.arsw.luka.lukaBack.domain.Subasta;
import edu.arsw.luka.lukaBack.exception.LukaException;

public interface SubastaRepositorio {
    
    Subasta agregarSubasta(Subasta subasta) throws LukaException;

    Subasta modificarFechaSubasta(String nombre,LocalDateTime fechaInicio, LocalDateTime fechaFin) throws LukaException;

    List<Subasta> consultarTodasLasSubastas();
    
    Subasta consultarSubastaPorNombre(String nombre) throws LukaException;

    void eliminarSubasta(String nombre) throws LukaException;

    public void agregarProductoSubasta(String nombre, Producto producto) throws LukaException;

    void eliminarProductoSubasta(String nombre, String idProducto) throws LukaException;


    
}
