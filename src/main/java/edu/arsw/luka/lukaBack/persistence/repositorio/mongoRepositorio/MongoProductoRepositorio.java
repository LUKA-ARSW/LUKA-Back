package edu.arsw.luka.lukaBack.persistence.repositorio.mongoRepositorio;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import edu.arsw.luka.lukaBack.domain.Producto;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.persistence.repositorio.ProductoRepositorio;

@Repository
public class MongoProductoRepositorio implements ProductoRepositorio {

    @Override
    public Producto agregarProducto(Producto producto) throws LukaException {
        return null;
    }

    
    
}
