package edu.arsw.luka.lukaBack.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Subasta consultarSubastaPorNombre(String nombre) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarSubastaPorNombre'");
    }

    @Override
    public void eliminarSubasta(String nombre) throws LukaException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarSubasta'");
    }

    @Override
    public Producto agregarProductoSubasta(String nombre, Producto producto) throws LukaException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'agregarProductoSubasta'");
    }

    @Override
    public void eliminarProductoSubasta(String nombre, String idProducto) throws LukaException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarProductoSubasta'");
    }
    
}
