package edu.arsw.luka.lukaBack.services;


import java.time.LocalDateTime;
import java.util.List;

import edu.arsw.luka.lukaBack.domain.Estado;
import edu.arsw.luka.lukaBack.domain.Producto;
import edu.arsw.luka.lukaBack.domain.Subasta;
import edu.arsw.luka.lukaBack.exception.LukaException;

public interface SubastaServicio {

    Subasta agregarSubasta(Subasta subasta) throws LukaException;

    List<Subasta>consultarTodasLasSubastas();
    Subasta consultarSubastaPorNombre(String nombre) throws LukaException;

    Subasta modificarFechaSubasta(String nombre,LocalDateTime fechaInicio, LocalDateTime fechaFin) throws LukaException;

    Subasta modificarEstadoSubasta(String nombre) throws LukaException;
    void eliminarSubasta(String nombre) throws LukaException;
    void agregarProductoSubasta(String nombre, Producto producto) throws LukaException;
    void eliminarProductoSubasta(String nombre, String idProducto) throws LukaException;
    
}
