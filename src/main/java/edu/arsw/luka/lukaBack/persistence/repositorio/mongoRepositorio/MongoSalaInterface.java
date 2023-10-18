package edu.arsw.luka.lukaBack.persistence.repositorio.mongoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

import edu.arsw.luka.lukaBack.domain.entity.SalaEntidad;

public interface MongoSalaInterface extends MongoRepository<SalaEntidad, String>{


    
}
