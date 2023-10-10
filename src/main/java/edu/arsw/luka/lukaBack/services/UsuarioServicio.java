package edu.arsw.luka.lukaBack.services;


import org.springframework.web.bind.annotation.RequestBody;

import edu.arsw.luka.lukaBack.domain.Usuario;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.exception.LukaLoginException;

public interface UsuarioServicio {

    Usuario crearUsuario(Usuario usuario) throws LukaException;
    Usuario login(String correo, String contrasena) throws LukaException, LukaLoginException;
    
}
