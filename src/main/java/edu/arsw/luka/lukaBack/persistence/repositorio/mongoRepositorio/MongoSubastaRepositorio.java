package edu.arsw.luka.lukaBack.persistence.repositorio.mongoRepositorio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.arsw.luka.lukaBack.domain.Producto;
import edu.arsw.luka.lukaBack.domain.Subasta;
import edu.arsw.luka.lukaBack.domain.TipoSubasta;
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
        subastaEntidad.setTipoSubasta(subasta.getTipoSubasta());

        subastaEntidad.setProductos(subasta.getProductos().stream().map(producto -> 
            new ProductoEntidad( 
                producto.getIdProducto(),
                producto.getNombre(), 
                producto.getDescripcion(), 
                producto.getFoto(), 
                producto.getPrecio(), 
                producto.getCategoria(),
                producto.getVendedor()
            )
        ).collect(Collectors.toList()));

        subastaEntidad.setEstado(subasta.getEstado());
        SubastaEntidad subastaResult=mongoSubastaInterface.save(subastaEntidad);


        return new Subasta(
            subastaResult.getNombre(),
            subastaResult.getFechaInicio(),
            subastaResult.getFechaFin(),
            subastaResult.getTipoSubasta(),
            subastaResult.getEstado(),

            subastaResult.getProductos().stream().map(producto -> 
                new Producto( 
                    producto.getIdProducto(),
                    producto.getNombre(), 
                    producto.getDescripcion(), 
                    producto.getFoto(), 
                    producto.getPrecio(), 
                    producto.getCategoria(),
                    producto.getVendedor()
                )
            ).collect(Collectors.toList())
           
        );

    }

    @Override
    public Subasta modificarFechaSubasta(String nombre,LocalDateTime fechaInicio, LocalDateTime fechaFin, boolean cambiarTipo) throws LukaException{
        SubastaEntidad subastaEntidad = mongoSubastaInterface.findById(nombre)
                                        .orElseThrow(() -> new LukaException("No existe la subasta"));
        if(!subastaEntidad.getTipoSubasta().fechasCoinciden(fechaInicio, fechaFin) && !cambiarTipo){
            throw new LukaException("Las fechas no coinciden con el tipo de subasta");
        }else if (cambiarTipo){
            subastaEntidad.setTipoSubasta(
                switch (subastaEntidad.getTipoSubasta()) {
                    case CORTA -> TipoSubasta.LARGA;
                    case LARGA -> TipoSubasta.CORTA;
                }
            );
        }
        subastaEntidad.setFechaInicio(fechaInicio);
        subastaEntidad.setFechaFin(fechaFin);
        SubastaEntidad subastaResult=mongoSubastaInterface.save(subastaEntidad);
        return new Subasta(
            subastaResult.getNombre(),
            subastaResult.getFechaInicio(),
            subastaResult.getFechaFin(),
            subastaResult.getTipoSubasta(),
            subastaResult.getEstado(),
            subastaResult.getProductos().stream().map(producto -> 
                new Producto( 
                    producto.getIdProducto(),
                    producto.getNombre(), 
                    producto.getDescripcion(), 
                    producto.getFoto(), 
                    producto.getPrecio(), 
                    producto.getCategoria(),
                    producto.getVendedor()
                )
            ).collect(Collectors.toList())
           
        );
    }

    @Override
    public List<Subasta> consultarTodasLasSubastas() {
        return mongoSubastaInterface.findAll().stream().map(subasta -> 
            new Subasta(
                subasta.getNombre(),
                subasta.getFechaInicio(),
                subasta.getFechaFin(),
                subasta.getTipoSubasta(),
                subasta.getEstado(),
                subasta.getProductos().stream().map(producto -> 
                    new Producto( 
                        producto.getIdProducto(),
                        producto.getNombre(), 
                        producto.getDescripcion(), 
                        producto.getFoto(), 
                        producto.getPrecio(), 
                        producto.getCategoria(),
                        producto.getVendedor()
                    )
                ).collect(Collectors.toList())
            )
        ).collect(Collectors.toList());
    }

    @Override
    public void eliminarSubasta(String nombre) {
        mongoSubastaInterface.deleteById(nombre);
        
    }

    @Override
    public Subasta consultarSubastaPorNombre(String nombre) throws LukaException{
        SubastaEntidad subasta= mongoSubastaInterface.findById(nombre).orElseThrow(() -> new LukaException("No existe la subasta"));
        return new Subasta(
            subasta.getNombre(),
            subasta.getFechaInicio(),
            subasta.getFechaFin(),
            subasta.getTipoSubasta(),
            subasta.getEstado(),
            subasta.getProductos().stream().map(producto -> 
                new Producto( 
                    producto.getIdProducto(),
                    producto.getNombre(), 
                    producto.getDescripcion(), 
                    producto.getFoto(), 
                    producto.getPrecio(), 
                    producto.getCategoria(),
                    producto.getVendedor()
                )
            ).collect(Collectors.toList())
        );
       
    }

    @Override
    public void agregarProductoSubasta(String nombre, Producto producto) throws LukaException {
        if(existeProducto(nombre, producto.getIdProducto())){
            throw new LukaException("El producto ya existe en la subasta");
        }
        Subasta subasta =consultarSubastaPorNombre(nombre);
        subasta.agregarProducto(producto);
        agregarSubasta(subasta);

    }

    @Override
    public void eliminarProductoSubasta(String nombre, String idProducto) throws LukaException {
        Subasta subasta =consultarSubastaPorNombre(nombre);
        subasta.eliminarProducto(idProducto);
        agregarSubasta(subasta);
        
    }

    @Override
    public boolean existeProducto(String nombre, String idProducto) throws LukaException {
        SubastaEntidad subastaEntidad = mongoSubastaInterface.findById(nombre)
                                        .orElseThrow(() -> new LukaException("No existe la subasta"));
        return subastaEntidad.getProductos().stream().anyMatch(producto -> producto.getIdProducto().equals(idProducto));       
       
    }

    
}
