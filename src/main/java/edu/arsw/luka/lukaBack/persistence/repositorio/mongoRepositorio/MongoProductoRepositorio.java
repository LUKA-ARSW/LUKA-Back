package edu.arsw.luka.lukaBack.persistence.repositorio.mongoRepositorio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import edu.arsw.luka.lukaBack.domain.Categoria;
import edu.arsw.luka.lukaBack.domain.Producto;
import edu.arsw.luka.lukaBack.domain.entity.ProductoEntidad;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.persistence.repositorio.ProductoRepositorio;

@Repository
public class MongoProductoRepositorio implements ProductoRepositorio {

    private MongoProductoInterface mongoProductoInterface;

    @Autowired
    public MongoProductoRepositorio(MongoProductoInterface mongoProductoInterface) {
        this.mongoProductoInterface = mongoProductoInterface;
    }

   @Override
    public Producto agregarProducto(Producto producto) throws LukaException {
        ProductoEntidad productoEntidad = new ProductoEntidad();
        productoEntidad.setIdProducto(producto.getIdProducto());
        productoEntidad.setNombre(producto.getNombre());
        productoEntidad.setDescripcion(producto.getDescripcion());
        productoEntidad.setFoto(producto.getFoto());
        productoEntidad.setPrecio(producto.getPrecio());
        productoEntidad.setCategoria(producto.getCategoria());

        ProductoEntidad productoResult = mongoProductoInterface.save(productoEntidad);

        return new Producto( 
            productoResult.getIdProducto(),
            productoResult.getNombre(), 
            productoResult.getDescripcion(), 
            productoResult.getFoto(), 
            productoResult.getPrecio(), 
            productoResult.getCategoria()
        );

    }

    @Override
    public List<Producto> consultarTodosLosProductos() {
        return mongoProductoInterface.findAll().stream().map(producto -> 
            new Producto( 
                producto.getIdProducto(),
                producto.getNombre(), 
                producto.getDescripcion(), 
                producto.getFoto(), 
                producto.getPrecio(), 
                producto.getCategoria()
            )
        ).collect(Collectors.toList());
    }

    @Override
    public Producto consultarProductoPorIdProducto(String idProducto) throws LukaException{
        ProductoEntidad productoEntidad = mongoProductoInterface.findById(idProducto)
                                        .orElseThrow(() -> new LukaException("Producto no encontrado"));

        return  new Producto( 
                productoEntidad.getIdProducto(),
                productoEntidad.getNombre(), 
                productoEntidad.getDescripcion(), 
                productoEntidad.getFoto(), 
                productoEntidad.getPrecio(), 
                productoEntidad.getCategoria()
        );
    }

    @Override
    public List<Producto> consultarProductoPorNombre(String nombre) throws LukaException {
        return mongoProductoInterface.findByNombre(nombre).stream().map(producto -> 
            new Producto( 
                producto.getIdProducto(),
                producto.getNombre(), 
                producto.getDescripcion(), 
                producto.getFoto(), 
                producto.getPrecio(), 
                producto.getCategoria()
            )
        ).collect(Collectors.toList());

    }


    @Override
    public Producto modificarProducto(String idProducto, Producto producto) throws LukaException {
        ProductoEntidad productoEntidad = mongoProductoInterface.findById(idProducto)
                                        .orElseThrow(() -> new LukaException("Producto no encontrado"));

        productoEntidad.setNombre(producto.getNombre());
        productoEntidad.setDescripcion(producto.getDescripcion());
        productoEntidad.setFoto(producto.getFoto());
        productoEntidad.setPrecio(producto.getPrecio());
        productoEntidad.setCategoria(producto.getCategoria());

        ProductoEntidad productoResult = mongoProductoInterface.save(productoEntidad);

        return new Producto( 
            productoResult.getIdProducto(),
            productoResult.getNombre(), 
            productoResult.getDescripcion(), 
            productoResult.getFoto(), 
            productoResult.getPrecio(), 
            productoResult.getCategoria()
        );
    }

    @Override
    public void eliminarProducto(String idProducto) throws LukaException {
        mongoProductoInterface.deleteById(idProducto);
    }

}
