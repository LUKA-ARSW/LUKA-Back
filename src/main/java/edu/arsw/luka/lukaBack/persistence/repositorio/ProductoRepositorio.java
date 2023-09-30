package edu.arsw.luka.lukaBack.persistence.repositorio;

import edu.arsw.luka.lukaBack.domain.Producto;
import edu.arsw.luka.lukaBack.exception.LukaException;

public interface ProductoRepositorio {

    Producto agregarProducto(Producto producto) throws LukaException;
    
}
