package edu.arsw.luka.lukaBack.services;



import edu.arsw.luka.lukaBack.domain.Comprador;
import edu.arsw.luka.lukaBack.domain.Usuario;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.exception.LukaLoginException;

public interface UsuarioServicio {

    Usuario crearUsuario(Usuario usuario) throws LukaException;
    Usuario login(String correo, String contrasena) throws LukaException, LukaLoginException;
    Usuario consultarUsuarioPorCorreo(String correo) throws LukaException;
    Comprador crearComprador(Comprador comprador) throws LukaException;
    
}
