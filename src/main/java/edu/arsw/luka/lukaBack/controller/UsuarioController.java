package edu.arsw.luka.lukaBack.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.arsw.luka.lukaBack.domain.Comprador;
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
    public ResponseEntity<?> crearUsuario(@RequestBody Map<String,String>usuario) {
        try{
            var usuarioCreado= usuarioServicio.crearUsuario(usuario);
            return ResponseEntity.status(201).body("Usuario :" + usuarioCreado.getNombre() +" creado");
        }catch(Exception e){
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody Map<String,String> inicioSesion) {
        try{
            String correo = inicioSesion.get("correo");
            String contrasena = inicioSesion.get("contrasena");            
            var result = usuarioServicio.login(correo, contrasena);
            return ResponseEntity.status(201).body(result.toString());
        }catch(Exception e){
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    /*@GetMapping(value = "/{correo}")
    public ResponseEntity<?> getUsuarioporcorreo(@PathVariable(required =true, value ="correo") String correo) {
        try{
            var result= usuarioServicio.consultarUsuarioPorCorreo(correo);
            return ResponseEntity.status(200).body(result);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


    @PostMapping(value = "/comprador")
    public ResponseEntity<?> crearComprador(@RequestBody Comprador comprador) {
        try{
            usuarioServicio.crearComprador(comprador);
            return ResponseEntity.status(201).body("Comprador :" + comprador.getNombre() +" creado");
        }catch(Exception e){
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }*/

    
}
