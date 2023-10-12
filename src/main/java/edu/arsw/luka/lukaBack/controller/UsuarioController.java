package edu.arsw.luka.lukaBack.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.arsw.luka.lukaBack.domain.Usuario;
import edu.arsw.luka.lukaBack.services.SubastaServicio;
import edu.arsw.luka.lukaBack.services.UsuarioServicio;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    private UsuarioServicio usuarioServicio;

    @Autowired
    public UsuarioController(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        try{
            usuarioServicio.crearUsuario(usuario);
            return ResponseEntity.status(201).body("Usuario :" + usuario.getNombre() +" creado");
        }catch(Exception e){
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> inicioSesion) {
        try{
            String correo = inicioSesion.get("correo");
            String contrasena = inicioSesion.get("contrasena");
            
            var result = usuarioServicio.login(correo, contrasena);
            return ResponseEntity.status(201).body(result);
        }catch(Exception e){
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }


    
}