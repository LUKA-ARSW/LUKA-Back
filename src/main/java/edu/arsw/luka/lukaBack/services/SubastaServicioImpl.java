package edu.arsw.luka.lukaBack.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.arsw.luka.lukaBack.domain.Estado;
import edu.arsw.luka.lukaBack.domain.Producto;
import edu.arsw.luka.lukaBack.domain.Subasta;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.persistence.repositorio.SubastaRepositorio;

@Service
public class SubastaServicioImpl implements SubastaServicio {

    @Autowired
    private SubastaRepositorio subastaRepositorio;

    @Override
    public Subasta agregarSubasta(Subasta subasta) throws LukaException{
        return subastaRepositorio.agregarSubasta(subasta);

    }

    @Override
    public Subasta modificarFechaSubasta(String nombre,LocalDateTime fechaInicio, LocalDateTime fechaFin) throws LukaException{

        if(fechaInicio!=null && fechaInicio.isAfter(fechaFin)){
            throw new LukaException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }

        if(fechaFin!=null && fechaFin.isBefore(fechaInicio)){
            throw new LukaException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }
        return subastaRepositorio.modificarFechaSubasta(nombre,fechaInicio, fechaFin);
        
    }


    @Override
    public List<Subasta> consultarTodasLasSubastas() {
        return subastaRepositorio.consultarTodasLasSubastas();
        
    }

    @Override
    public Subasta consultarSubastaPorNombre(String nombre) throws LukaException{
        return subastaRepositorio.consultarSubastaPorNombre(nombre);
        
    }

    @Override
    public Subasta modificarEstadoSubasta(String nombre) throws LukaException{
        Subasta result=consultarSubastaPorNombre(nombre);
        LocalDateTime fechaComparar = LocalDateTime.now();

        if(fechaComparar.isBefore(result.getFechaInicio())){
            result.setEstado(Estado.PROGRAMADA);
            
        }else if(fechaComparar.isAfter(result.getFechaFin())){
            result.setEstado(Estado.FINALIZADA);
        }else{
            result.setEstado(Estado.EN_CURSO);
        }

        return subastaRepositorio.agregarSubasta(result);

    }

    @Override
    public void eliminarSubasta(String nombre) throws LukaException {
        subastaRepositorio.eliminarSubasta(nombre);
    }

    @Override
    public void agregarProductoSubasta(String nombre, Producto producto) throws LukaException {
        subastaRepositorio.agregarProductoSubasta(nombre, producto);
    }

    @Override
    public void eliminarProductoSubasta(String nombre, String idProducto) throws LukaException {
        subastaRepositorio.eliminarProductoSubasta(nombre, idProducto);
    }

    
    
}
