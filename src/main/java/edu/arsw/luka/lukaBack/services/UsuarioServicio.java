package edu.arsw.luka.lukaBack.services;



import java.util.Map;

import edu.arsw.luka.lukaBack.domain.Comprador;
import edu.arsw.luka.lukaBack.domain.Usuario;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.exception.LukaLoginException;
import edu.arsw.luka.lukaBack.util.JsonWebToken;


public interface UsuarioServicio {

    Usuario crearUsuario (Map<String,String> usuario) throws LukaException;
    String login(String correo, String contrasena) throws LukaException, LukaLoginException;
    String logout(String token) throws LukaException;
    Usuario consultarUsuarioPorCorreo(String correo) throws LukaException;
    Comprador crearComprador(Comprador comprador) throws LukaException;
    
}
