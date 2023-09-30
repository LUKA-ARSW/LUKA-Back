package edu.arsw.luka.lukaBack.services;

import edu.arsw.luka.lukaBack.domain.Producto;
import edu.arsw.luka.lukaBack.exception.LukaException;

public interface ProductoServicio {
    Producto agregarProducto(Producto producto) throws LukaException;
    
}
