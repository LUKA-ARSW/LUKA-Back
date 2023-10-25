package edu.arsw.luka.lukaBack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.arsw.luka.lukaBack.domain.Usuario;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.exception.LukaLoginException;
import edu.arsw.luka.lukaBack.persistence.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public Usuario crearUsuario(Usuario usuario) throws LukaException {
        return usuarioRepositorio.crearUsuario(usuario);
    }

    @Override
    public Usuario login(String correo, String contrasena) throws LukaException, LukaLoginException {
        return usuarioRepositorio.login(correo, contrasena);
    }

    @Override
    public Usuario consultarUsuarioPorCorreo(String correo) throws LukaException {
        return usuarioRepositorio.consultarUsuarioPorCorreo(correo);
    }
    
    
}
