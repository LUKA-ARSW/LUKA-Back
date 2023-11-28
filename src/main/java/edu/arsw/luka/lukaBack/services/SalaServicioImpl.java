package edu.arsw.luka.lukaBack.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.arsw.luka.lukaBack.domain.Estado;
import edu.arsw.luka.lukaBack.domain.Sala;
import edu.arsw.luka.lukaBack.domain.Subasta;
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
        if(!salaRepositorio.existeComprador(nombre, comprador)){ throw new LukaException("El comprador no existe");}
        salaRepositorio.pujarPorProducto(nombre,cantidadAPujar, comprador, idProducto);
    }
    
    @Override
    public List<Subasta> consultarSubastasPorUsuario(String comprador,Estado estado){  
        
        var subastasPorComprador = this.consultarTodasLasSalas().stream()
            .filter(sala -> sala.getCompradores().contains(comprador));
        
        if (estado != null) {
            subastasPorComprador = subastasPorComprador
                .filter(sala -> sala.getSubasta().getEstado().equals(estado));            
        }

        return subastasPorComprador
            .map(Sala::getSubasta)
            .toList();       
    }

    @Override
    public Sala consultarSalasPorSubasta(String nombreSubasta) throws LukaException{
        return this.consultarTodasLasSalas().stream()
            .filter(sala -> sala.getSubasta().getNombre().equals(nombreSubasta))
            .findFirst()
            .orElseThrow(() -> new LukaException("No se encontro la sala con la subasta: " + nombreSubasta));
    }
}
