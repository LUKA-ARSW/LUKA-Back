package edu.arsw.luka.lukaBack.persistence.repositorio.mongoRepositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.arsw.luka.lukaBack.domain.entity.SubastaEntidad;

public interface MongoSubastaInterface extends MongoRepository<SubastaEntidad, String> {
    
    
}
