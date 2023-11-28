package edu.arsw.luka.lukaBack.services;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import edu.arsw.luka.lukaBack.domain.Estado;
import edu.arsw.luka.lukaBack.domain.Sala;
import edu.arsw.luka.lukaBack.domain.Subasta;
import edu.arsw.luka.lukaBack.exception.LukaException;

public interface SalaServicio {
    Sala agregarSala(Sala sala) throws LukaException;

    List<Sala> consultarTodasLasSalas();

    Sala consultarSalaPorNombre(String nombre) throws LukaException;

    void eliminarSala(String nombre) throws LukaException;

    void agregarUsuario(String nombre,String correo) throws LukaException;

    void eliminarUsuario(String nombre,String correo) throws LukaException;

    void pujarPorProducto(String nombre, double cantidadAPujar, String comprador, String idProducto) throws LukaException;
    
    List<Subasta> consultarSubastasPorUsuario(String comprador, Estado estado);

    Sala consultarSalasPorSubasta(String nombreSubasta) throws LukaException;




    
}
