package edu.arsw.luka.lukaBack.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.arsw.luka.lukaBack.domain.Categoria;
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

    @Override
    public List<Producto> consultarTodosLosProductos() {
        return productoRepositorio.consultarTodosLosProductos();
    }

    @Override
    public Producto consultarProductoPorIdProducto(String idProducto) throws LukaException {
        return productoRepositorio.consultarProductoPorIdProducto(idProducto);
    }

    @Override
    public List<Producto> consultarProductoPorNombre(String nombre) throws LukaException {
        return productoRepositorio.consultarProductoPorNombre(nombre);
    }

    @Override
    public Producto modificarProducto(String idProducto, Producto producto) throws LukaException {
        return productoRepositorio.modificarProducto(idProducto, producto);
    }

    @Override
    public void eliminarProducto(String idProducto) throws LukaException {
        productoRepositorio.eliminarProducto(idProducto);
    }

    
}
