package edu.arsw.luka.lukaBack.persistence.repositorio.mongoRepositorio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.client.model.Collation;

import edu.arsw.luka.lukaBack.domain.ElementoSubasta;
import edu.arsw.luka.lukaBack.domain.Sala;
import edu.arsw.luka.lukaBack.domain.Subasta;
import edu.arsw.luka.lukaBack.domain.entity.ElementoSubastaEntidad;
import edu.arsw.luka.lukaBack.domain.entity.ProductoEntidad;
import edu.arsw.luka.lukaBack.domain.entity.SalaEntidad;
import edu.arsw.luka.lukaBack.domain.entity.SubastaEntidad;
import edu.arsw.luka.lukaBack.domain.entity.UsuarioEntidad;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.persistence.repositorio.SalaRepositorio;

@Repository
public class MongoSalaRepositorio implements SalaRepositorio{
    
    private MongoSalaInterface mongoSalaInterface;
    private MongoSubastaRepositorio mongoSubastaRepositorio;
    private MongoProductoRepositorio mongoProductoRepositorio;
    private MongoUsuarioRepositorio mongoUsuarioRepositorio;

    @Autowired
    public MongoSalaRepositorio(MongoSalaInterface mongoSalaInterface,MongoSubastaRepositorio mongoSubastaRepositorio, MongoProductoRepositorio mongoProductoRepositorio, MongoUsuarioRepositorio  mongoUsuariorRepositorio) {
        this.mongoSalaInterface = mongoSalaInterface;
        this.mongoSubastaRepositorio = mongoSubastaRepositorio;
        this.mongoProductoRepositorio = mongoProductoRepositorio;
        this.mongoUsuarioRepositorio = mongoUsuariorRepositorio;
    }

    @Override
    public Sala agregarSala(Sala sala) throws LukaException {
        SalaEntidad salaEntidad = new SalaEntidad();
        salaEntidad.setNombre(sala.getNombre());
        salaEntidad.setSubasta(sala.getSubasta().getNombre());
        salaEntidad.setCompradores(sala.getCompradores());

        salaEntidad.setElementoSubasta(sala.getElementoSubasta().stream().map(elementoSubasta ->
            new ElementoSubastaEntidad(
                elementoSubasta.getProducto().getIdProducto(),
                elementoSubasta.getPujaMaxima(),
                elementoSubasta.getCompradores()      
            )
        ).collect(Collectors.toList()));
        SalaEntidad salaResult=mongoSalaInterface.save(salaEntidad);

        sala.setNombre(salaResult.getNombre());

        return sala;
        
    }


    @Override
    public List<Sala> consultarTodasLasSalas() {
        return mongoSalaInterface.findAll().parallelStream().map(sala ->
            {
                try {
                    return this.consultarSalaPorNombre(sala.getNombre());
                } catch (LukaException e) {
                    return null;
                }
            }
        ).toList();
    }

    @Override
    public Sala consultarSalaPorNombre(String nombre) throws LukaException {
        SalaEntidad salaEntidad = mongoSalaInterface.findById(nombre)
                                .orElseThrow(() -> new LukaException("No se encontro la sala con el nombre: " + nombre));

        Subasta subasta= mongoSubastaRepositorio.consultarSubastaPorNombre(salaEntidad.getSubasta());
        Collection<ElementoSubasta> elementoSubasta = new ArrayList<>();
        salaEntidad.getElementoSubasta().stream().forEach(elementoSubastaEntidad -> {
                
                try {
                    var result = ElementoSubasta.builder()
                        .producto(mongoProductoRepositorio.consultarProductoPorIdProducto(elementoSubastaEntidad.getIdProducto()))
                        .pujaMaxima(elementoSubastaEntidad.getPujaMaxima())
                        .build();
                    result.inicializarElementoSubasta(elementoSubastaEntidad.getCompradores());
                    elementoSubasta.add(result);
                } catch (LukaException e) {                    
                }
            }
        );
        var compradores = salaEntidad.getCompradores();

        return new Sala(
            salaEntidad.getNombre(),
            subasta,
            compradores,
            elementoSubasta
        );
    }

    @Override
    public void eliminarSala(String nombre) throws LukaException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarSala'");
    }

    @Override
    public void agregarUsuario(String nombre, String correo) throws LukaException{
        SalaEntidad salaEntidad = mongoSalaInterface.findById(nombre)
                                .orElseThrow(() -> new LukaException("No se encontro la sala con el nombre: " + nombre));
        if(mongoUsuarioRepositorio.existeUsuario(correo)){
            if(existeComprador(nombre, correo)){
                throw new LukaException("El usuario ya esta en la sala");
            }
            salaEntidad.getCompradores().add(correo);
            mongoSalaInterface.save(salaEntidad);
        }else{
            throw new LukaException("No existe el usuario");
        }

    }

    @Override
    public void eliminarUsuario(String nombre, String correo) throws LukaException{
        SalaEntidad salaEntidad = mongoSalaInterface.findById(nombre)
                                .orElseThrow(() -> new LukaException("No se encontro la sala con el nombre: " + nombre));
        if(mongoUsuarioRepositorio.existeUsuario(correo)){
            if(!existeComprador(nombre, correo)){
                throw new LukaException("El usuario no esta en la sala");
            }
            salaEntidad.getCompradores().remove(correo);
            mongoSalaInterface.save(salaEntidad);
        }else{
            throw new LukaException("No existe el usuario");
        }
    }

    @Override
    public boolean existeComprador(String nombre, String correo) throws LukaException{
        SalaEntidad salaEntidad = mongoSalaInterface.findById(nombre)
                            .orElseThrow(() -> new LukaException("No se encontro la sala con el nombre: " + nombre));
        return salaEntidad.getCompradores().contains(correo);


    }

    @Override
    public void pujarPorProducto(String nombre, double cantidadAPujar, String comprador, String idProducto) throws LukaException {
        var sala = consultarSalaPorNombre(nombre);
        var compradorRepo = mongoUsuarioRepositorio.getCompradorPorId(comprador);
        sala.pujarPorProducto(cantidadAPujar, compradorRepo, idProducto);
        compradorRepo.getCuentaBancaria().quitarFondos(cantidadAPujar);
        mongoUsuarioRepositorio.crearComprador(compradorRepo);
        agregarSala(sala);

    }
    
}
