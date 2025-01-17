package edu.arsw.luka.lukaBack.persistence.repositorio;


import org.springframework.data.util.Pair;

import edu.arsw.luka.lukaBack.domain.Comprador;
import edu.arsw.luka.lukaBack.domain.CuentaBancaria;
import edu.arsw.luka.lukaBack.domain.Usuario;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.exception.LukaLoginException;

public interface UsuarioRepositorio {

    Usuario crearUsuario(Usuario usuario) throws LukaException;
    Usuario crearUsuario(Usuario usuario, String numeroCuenta) throws LukaException;
    Pair<Usuario,CuentaBancaria> login(String correo, String contrasena) throws LukaException, LukaLoginException;
    Usuario consultarUsuarioPorCorreo(String correo) throws LukaException;
    Comprador crearComprador(Comprador comprador) throws LukaException;
}
