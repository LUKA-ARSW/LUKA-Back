package edu.arsw.luka.lukaBack.persistence.repositorio.mongoRepositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.arsw.luka.lukaBack.domain.entity.ProductoEntidad;

public interface MongoProductoInterface extends MongoRepository<ProductoEntidad, String> {
    List<ProductoEntidad> findByNombre(String nombre);
    
}
