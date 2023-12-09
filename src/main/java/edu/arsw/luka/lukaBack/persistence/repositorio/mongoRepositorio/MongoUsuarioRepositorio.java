package edu.arsw.luka.lukaBack.persistence.repositorio.mongoRepositorio;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import edu.arsw.luka.lukaBack.domain.Comprador;
import edu.arsw.luka.lukaBack.domain.CuentaBancaria;
import edu.arsw.luka.lukaBack.domain.Rol;
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
    public Usuario crearUsuario(Usuario usuario) throws LukaException{
        return this.crearUsuario(usuario, usuario.getNumDocumento());
    }

    @Override
    public Usuario crearUsuario(Usuario usuario, String numeroCuenta) throws LukaException {
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setNombre(usuario.getNombre());
        usuarioEntidad.setNombreUsuario(usuario.getNombreUsuario());
        usuarioEntidad.setCorreo(usuario.getCorreo());
        usuarioEntidad.setTipoDocumento(usuario.getTipoDocumento());
        usuarioEntidad.setNumDocumento(usuario.getNumDocumento());
        usuarioEntidad.setContrasena(usuario.getContrasena());
        usuarioEntidad.setRol(usuario.getRol());

        if (!usuarioEntidad.getRol().equals(Rol.ADMINISTRADOR)) {
            usuarioEntidad.setCuentaBancaria(new CuentaBancaria(numeroCuenta));
        }

        UsuarioEntidad usuarioResult = mongoUsuarioInterface.save(usuarioEntidad);

        return new Usuario(
            usuarioResult.getNombre(),
            usuarioResult.getNombreUsuario(),
            usuarioResult.getCorreo(),
            usuarioResult.getTipoDocumento(),
            usuarioResult.getNumDocumento(),
            usuarioResult.getContrasena(),
            usuarioResult.getRol()

        );
    }

    @Override
    public Pair<Usuario,CuentaBancaria> login(String correo, String contrasena) throws LukaException, LukaLoginException {
        UsuarioEntidad usuarioEntidad = mongoUsuarioInterface.findByCorreoAndContrasena(correo, contrasena);
        
        if(usuarioEntidad==null)
            throw new LukaLoginException("El usuario o contraseña no son validos");

        return Pair.of(
            new Usuario(
                usuarioEntidad.getNombre(),
                usuarioEntidad.getNombreUsuario(),
                usuarioEntidad.getCorreo(),
                usuarioEntidad.getTipoDocumento(),
                usuarioEntidad.getNumDocumento(),
                usuarioEntidad.getContrasena(),
                usuarioEntidad.getRol()
            ),
            usuarioEntidad.getCuentaBancaria()
        );

    }

   @Override
    public Usuario consultarUsuarioPorCorreo(String correo) throws LukaException {
        UsuarioEntidad usuarioEntidad = mongoUsuarioInterface.findById(correo).orElseThrow(() -> new LukaException("No existe el usuario"));

        return new Usuario(
            usuarioEntidad.getNombre(),
            usuarioEntidad.getNombreUsuario(),
            usuarioEntidad.getCorreo(),
            usuarioEntidad.getTipoDocumento(),
            usuarioEntidad.getNumDocumento(), 
            usuarioEntidad.getContrasena(),
            usuarioEntidad.getRol()    
        );
    }

    public boolean existeUsuario(String correo) throws LukaException {
        return mongoUsuarioInterface.existsById(correo);
    }

    public Comprador getCompradorPorId(String correo) throws LukaException {
        UsuarioEntidad usuarioEntidad = mongoUsuarioInterface.findById(correo).orElseThrow(() -> new LukaException("No existe el usuario"));
        return new Comprador(
            usuarioEntidad.getNombre(),
            usuarioEntidad.getNombreUsuario(),
            usuarioEntidad.getCorreo(),
            usuarioEntidad.getTipoDocumento(),
            usuarioEntidad.getNumDocumento(), 
            usuarioEntidad.getContrasena(),
            usuarioEntidad.getCuentaBancaria()     
        );

    }

    public Comprador crearComprador(Comprador comprador) throws LukaException{
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setNombre(comprador.getNombre());
        usuarioEntidad.setNombreUsuario(comprador.getNombreUsuario());
        usuarioEntidad.setCorreo(comprador.getCorreo());
        usuarioEntidad.setTipoDocumento(comprador.getTipoDocumento());
        usuarioEntidad.setNumDocumento(comprador.getNumDocumento());
        usuarioEntidad.setContrasena(comprador.getContrasena());
        usuarioEntidad.setCuentaBancaria(comprador.getCuentaBancaria());

        UsuarioEntidad usuarioResult = mongoUsuarioInterface.save(usuarioEntidad);

        return new Comprador(
            usuarioResult.getNombre(),
            usuarioResult.getNombreUsuario(),
            usuarioResult.getCorreo(),
            usuarioResult.getTipoDocumento(),
            usuarioResult.getNumDocumento(),
            usuarioResult.getContrasena(),
            usuarioResult.getCuentaBancaria()
        );
    }
    
}
