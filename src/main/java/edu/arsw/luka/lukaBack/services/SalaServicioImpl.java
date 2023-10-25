package edu.arsw.luka.lukaBack.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.arsw.luka.lukaBack.domain.Sala;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.persistence.repositorio.SalaRepositorio;

@Service
public class SalaServicioImpl implements SalaServicio{
    @Autowired
    private SalaRepositorio salaRepositorio;

    @Override
    public Sala agregarSala(Sala sala) throws LukaException {
        return salaRepositorio.agregarSala(sala);

    }

    @Override
    public List<Sala> consultarTodasLasSalas() {
        return salaRepositorio.consultarTodasLasSalas();
    }

    @Override
    public Sala consultarSalaPorNombre(String nombre) throws LukaException {
        return salaRepositorio.consultarSalaPorNombre(nombre);
    }

    @Override
    public void eliminarSala(String nombre) throws LukaException {
        salaRepositorio.eliminarSala(nombre);
    }

    @Override
    public void agregarUsuario(String nombre, String correo) throws LukaException {
        salaRepositorio.agregarUsuario(nombre,correo);
       
    }

    @Override
    public void eliminarUsuario(String nombre, String correo) throws LukaException {
        salaRepositorio.eliminarUsuario(nombre,correo);
        
    }

    @Override
    public void pujarPorProducto(String nombre, double cantidadAPujar, String comprador, String idProducto) throws LukaException {
        salaRepositorio.pujarPorProducto(nombre,cantidadAPujar, comprador, idProducto);
    }
    
}
