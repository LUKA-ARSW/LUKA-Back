package edu.arsw.luka.lukaBack.persistence.repositorio.mongoRepositorio;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.arsw.luka.lukaBack.domain.Usuario;
import edu.arsw.luka.lukaBack.domain.entity.UsuarioEntidad;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.exception.LukaLoginException;
import edu.arsw.luka.lukaBack.persistence.repositorio.UsuarioRepositorio;

@Repository
public class MongoUsuarioRepositorio implements UsuarioRepositorio{

    private MongoUsuarioInterface mongoUsuarioInterface;

    @Autowired
    public MongoUsuarioRepositorio(MongoUsuarioInterface mongoUsuarioInterface) {
        this.mongoUsuarioInterface = mongoUsuarioInterface;
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) throws LukaException {
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setNombre(usuario.getNombre());
        usuarioEntidad.setNombreUsuario(usuario.getNombreUsuario());
        usuarioEntidad.setCorreo(usuario.getCorreo());
        usuarioEntidad.setTipoDocumento(usuario.getTipoDocumento());
        usuarioEntidad.setNumDocumento(usuario.getNumDocumento());
        usuarioEntidad.setContrasena(usuario.getContrasena());

        UsuarioEntidad usuarioResult = mongoUsuarioInterface.save(usuarioEntidad);

        return new Usuario(
            usuarioResult.getNombre(),
            usuarioResult.getNombreUsuario(),
            usuarioResult.getCorreo(),
            usuarioResult.getTipoDocumento(),
            usuarioResult.getNumDocumento(),
            usuarioResult.getContrasena()
        );
    }

    @Override
    public Usuario login(String correo, String contrasena) throws LukaException, LukaLoginException {
        UsuarioEntidad usuarioEntidad = mongoUsuarioInterface.findByCorreoAndContrasena(correo, contrasena);
        
        if(usuarioEntidad==null)
            throw new LukaLoginException("El usuario o contraseña no son validos");

        return new Usuario(
            usuarioEntidad.getNombre(),
            usuarioEntidad.getNombreUsuario(),
            usuarioEntidad.getCorreo(),
            usuarioEntidad.getTipoDocumento(),
            usuarioEntidad.getNumDocumento(),
            usuarioEntidad.getContrasena()
        );

    }
    
}
