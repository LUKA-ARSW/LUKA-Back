package edu.arsw.luka.lukaBack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.arsw.luka.lukaBack.domain.Producto;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.persistence.repositorio.ProductoRepositorio;

@Service
public class ProductoServicioImpl implements ProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Override
    public Producto agregarProducto(Producto producto) throws LukaException {
       return productoRepositorio.agregarProducto(producto);
    }
    
}
