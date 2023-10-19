package edu.arsw.luka.lukaBack.persistence.repositorio;

import java.util.List;

import edu.arsw.luka.lukaBack.domain.Categoria;
import edu.arsw.luka.lukaBack.domain.Producto;
import edu.arsw.luka.lukaBack.exception.LukaException;

public interface ProductoRepositorio {

    Producto agregarProducto(Producto producto) throws LukaException;
    List<Producto> consultarTodosLosProductos();
    Producto consultarProductoPorIdProducto(String idProducto) throws LukaException;
    List<Producto> consultarProductoPorNombre(String nombre) throws LukaException;
    Producto modificarProducto(String idProducto, Producto producto) throws LukaException;
    void eliminarProducto(String idProducto) throws LukaException;
    
}
