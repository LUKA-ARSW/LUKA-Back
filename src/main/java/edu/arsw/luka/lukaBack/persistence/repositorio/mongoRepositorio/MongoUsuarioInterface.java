package edu.arsw.luka.lukaBack.persistence.repositorio.mongoRepositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.arsw.luka.lukaBack.domain.entity.UsuarioEntidad;



public interface MongoUsuarioInterface extends MongoRepository<UsuarioEntidad, String> {
    UsuarioEntidad findByCorreoAndContrasena(String correo, String contrasena);
    
}
