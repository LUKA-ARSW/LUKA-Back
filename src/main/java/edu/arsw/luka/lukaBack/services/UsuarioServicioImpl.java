package edu.arsw.luka.lukaBack.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.arsw.luka.lukaBack.domain.Comprador;
import edu.arsw.luka.lukaBack.domain.Rol;
import edu.arsw.luka.lukaBack.domain.TipoDocumento;
import edu.arsw.luka.lukaBack.domain.Usuario;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.exception.LukaLoginException;
import edu.arsw.luka.lukaBack.persistence.repositorio.UsuarioRepositorio;
import edu.arsw.luka.lukaBack.util.JsonWebToken;
import edu.arsw.luka.lukaBack.util.WebToken;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private AutorizacionServicio autorizacionServicio;

    @Override
    public Usuario crearUsuario(Map<String,String> usuario) throws LukaException {
        var rol = Rol.valueOf(usuario.get("rol"));
        var tipoDocumento = TipoDocumento.valueOf(usuario.get("tipoDocumento"));
        var nuevoUsuario = new Usuario(
            usuario.get("nombre"),
            usuario.get("nombreUsuario"),
            usuario.get("correo"),
            tipoDocumento,
            usuario.get("numDocumento"),
            usuario.get("contrasena"),
            rol
        );
        return usuarioRepositorio.crearUsuario(nuevoUsuario);
    }

    @Override
    public String login(String correo, String contrasena) throws LukaException, LukaLoginException {
        var result = usuarioRepositorio.login(correo, contrasena);
        WebToken token = new WebToken();
        token.parseToken(result.getFirst());
        token.parseToken(result.getSecond());        
        return autorizacionServicio.crearToken(token);
    }

    @Override
    public String logout(String token) throws LukaException {
        autorizacionServicio.logout(token);        
        return "Deslogueo exitoso";
    }

    @Override
    public Usuario consultarUsuarioPorCorreo(String correo) throws LukaException {
        return usuarioRepositorio.consultarUsuarioPorCorreo(correo);
    }

    @Override
    public Comprador crearComprador(Comprador comprador) throws LukaException {
        return usuarioRepositorio.crearComprador(comprador);
    }
    
    
}
