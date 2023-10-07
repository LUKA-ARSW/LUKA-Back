package edu.arsw.luka.lukaBack.persistence.repositorio.mongoRepositorio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.arsw.luka.lukaBack.domain.Producto;
import edu.arsw.luka.lukaBack.domain.Subasta;
import edu.arsw.luka.lukaBack.domain.entity.ProductoEntidad;
import edu.arsw.luka.lukaBack.domain.entity.SubastaEntidad;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.persistence.repositorio.SubastaRepositorio;

@Repository
public class MongoSubastaRepositorio implements SubastaRepositorio{

    private MongoSubastaInterface mongoSubastaInterface;

    @Autowired
    public MongoSubastaRepositorio(MongoSubastaInterface mongoSubastaInterface) {
        this.mongoSubastaInterface = mongoSubastaInterface;
    }

    @Override
    public Subasta agregarSubasta(Subasta subasta) throws LukaException{
        SubastaEntidad subastaEntidad = new SubastaEntidad();
        subastaEntidad.setNombre(subasta.getNombre());
        subastaEntidad.setFechaInicio(subasta.getFechaInicio());
        subastaEntidad.setFechaFin(subasta.getFechaFin());

        subastaEntidad.setProductos(subasta.getProductos().stream().map(producto -> 
            new ProductoEntidad( 
                producto.getIdProducto(),
                producto.getNombre(), 
                producto.getDescripcion(), 
                producto.getFoto(), 
                producto.getPrecio(), 
                producto.getCategoria())
        ).collect(Collectors.toList()));

        subastaEntidad.setEstado(subasta.getEstado());
        SubastaEntidad subastaResult=mongoSubastaInterface.save(subastaEntidad);


        return new Subasta(
            subastaResult.getNombre(),
            subastaResult.getFechaInicio(),
            subastaResult.getFechaFin(),
             subastaResult.getEstado(),
            subastaResult.getProductos().stream().map(producto -> 
                new Producto( 
                    producto.getIdProducto(),
                    producto.getNombre(), 
                    producto.getDescripcion(), 
                    producto.getFoto(), 
                    producto.getPrecio(), 
                    producto.getCategoria())
            ).collect(Collectors.toList())
           
        );

    }

    @Override
    public Subasta modificarFechaSubasta(String nombre,LocalDateTime fechaInicio, LocalDateTime fechaFin) throws LukaException{
        return null;
    }

    @Override
    public List<Subasta> consultarTodasLasSubastas() {
        return mongoSubastaInterface.findAll().stream().map(subasta -> 
            new Subasta(
                subasta.getNombre(),
                subasta.getFechaInicio(),
                subasta.getFechaFin(),
                subasta.getEstado(),
                subasta.getProductos().stream().map(producto -> 
                    new Producto( 
                        producto.getIdProducto(),
                        producto.getNombre(), 
                        producto.getDescripcion(), 
                        producto.getFoto(), 
                        producto.getPrecio(), 
                        producto.getCategoria()
                    )
                ).collect(Collectors.toList())
            )
        ).collect(Collectors.toList());
    }


    
}
