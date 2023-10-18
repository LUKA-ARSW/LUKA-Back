package edu.arsw.luka.lukaBack.persistence.repositorio.mongoRepositorio;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import edu.arsw.luka.lukaBack.domain.Sala;
import edu.arsw.luka.lukaBack.domain.entity.ProductoEntidad;
import edu.arsw.luka.lukaBack.domain.entity.SalaEntidad;
import edu.arsw.luka.lukaBack.domain.entity.SubastaEntidad;
import edu.arsw.luka.lukaBack.domain.entity.UsuarioEntidad;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.persistence.repositorio.SalaRepositorio;

public class MongoSalaRepositorio implements SalaRepositorio{
    
    private MongoSalaInterface mongoSalaInterface;

    @Autowired
    public MongoSalaRepositorio(MongoSalaInterface mongoSalaInterface) {
        this.mongoSalaInterface = mongoSalaInterface;
    }

    @Override
    public Sala agregarSala(Sala sala) throws LukaException {
        SalaEntidad salaEntidad = new SalaEntidad();
        salaEntidad.setNombre(sala.getNombre());
        salaEntidad.setSubasta(sala.getSubasta().getNombre());

        salaEntidad.setCompradores(sala.getCompradores().stream().map(compradores ->
            compradores.getCorreo()
        ).collect(Collectors.toList()));

        salaEntidad.setElementoSubasta(null);
        SalaEntidad salaResult=mongoSalaInterface.save(salaEntidad);

        /*return new Sala(
            salaResult.getNombre(),
            salaResult.getSubasta(),
            salaResult.getCompradores().stream().map(compradores ->
                    compradores.getCorreo())
            ).collect(Collectors.toList()));*/
        return null;
        
    }

    @Override
    public List<Sala> consultarTodasLasSalas() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarTodasLasSalas'");
    }

    @Override
    public Sala consultarSalaPorNombre(String nombre) throws LukaException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarSalaPorNombre'");
    }

    @Override
    public void eliminarSala(String nombre) throws LukaException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarSala'");
    }
    
}
