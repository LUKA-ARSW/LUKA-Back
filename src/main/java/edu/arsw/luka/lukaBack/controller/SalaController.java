package edu.arsw.luka.lukaBack.controller;

import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import edu.arsw.luka.lukaBack.domain.Estado;
import edu.arsw.luka.lukaBack.domain.Sala;
import edu.arsw.luka.lukaBack.domain.Subasta;
import edu.arsw.luka.lukaBack.exception.LukaNoAutorizadoException;
import edu.arsw.luka.lukaBack.services.AutorizacionServicio;
import edu.arsw.luka.lukaBack.services.SalaServicio;
import edu.arsw.luka.lukaBack.services.SubastaServicio;

@RestController
@RequestMapping(value = "/sala")
public class SalaController {

    private SalaServicio salaServicio;
    private AutorizacionServicio autorizacionServicio;

    @Autowired
    public SalaController(SalaServicio salaServicio, AutorizacionServicio autorizacionServicio) {
        this.salaServicio = salaServicio;
        this.autorizacionServicio = autorizacionServicio;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> crearSala(@RequestBody Sala sala, @RequestHeader("Autorizacion") String token) {
        try{
            autorizacionServicio.autorizar(token);
            salaServicio.agregarSala(sala);
            return ResponseEntity.status(201).body("Sala :" + sala.getNombre() +" creada");
        }catch(LukaNoAutorizadoException e){
            return ResponseEntity.status(403).body(e.getMessage());
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getTodasLasSalas(@RequestHeader("Autorizacion") String token) {
        try{
            autorizacionServicio.autorizar(token);
            var result=salaServicio.consultarTodasLasSalas();
            return ResponseEntity.status(200).body(result);
        }catch(LukaNoAutorizadoException e){
            return ResponseEntity.status(403).body(e.getMessage());        
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @GetMapping(value = "/{nombre}")
    public ResponseEntity<?> getSalaPorNombre(@PathVariable(required =true, value ="nombre") String nombre, 
        @RequestHeader("Autorizacion") String token
    ) {  
        try{
            autorizacionServicio.autorizar(token);
            var result= salaServicio.consultarSalaPorNombre(nombre);
            return ResponseEntity.status(200).body(result);
        }catch(LukaNoAutorizadoException e){
            return ResponseEntity.status(403).body(e.getMessage());
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @GetMapping(value = "/comprador/{comprador}")
    public ResponseEntity<?> getSubastasPorUsuario(
        @PathVariable(required =true, value ="comprador") String comprador,
        @RequestParam(required =true, value ="estado") Estado estado,
        @RequestHeader("Autorizacion") String token
    ) {  
        try{
            autorizacionServicio.autorizar(token);
            var result= salaServicio.consultarSubastasPorUsuario(comprador,estado);
            return ResponseEntity.status(200).body(result);
        }catch(LukaNoAutorizadoException e){
            return ResponseEntity.status(403).body(e.getMessage());
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @GetMapping("/subasta/{nombreSubasta}")
    public ResponseEntity<?> getSalasPorSubasta(@PathVariable(required =true, value ="nombreSubasta") String nombreSubasta, 
        @RequestHeader("Autorizacion") String token
    ) {  
        try{
            autorizacionServicio.autorizar(token);
            var result= salaServicio.consultarSalasPorSubasta(nombreSubasta);
            return ResponseEntity.status(200).body(result);
        }catch(LukaNoAutorizadoException e){
            return ResponseEntity.status(403).body(e.getMessage());
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }
    
    @DeleteMapping(value = "/{nombre}")
    public ResponseEntity<?> eliminarSala(@PathVariable("nombre") String nombre, 
        @RequestHeader("Autorizacion") String token
    ) {
        try{
            autorizacionServicio.autorizar(token);
            salaServicio.eliminarSala(nombre);
            return ResponseEntity.status(200).body("La sala: " + nombre + " ha sido eliminado");
        }catch(LukaNoAutorizadoException e){
            return ResponseEntity.status(403).body(e.getMessage());
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @PostMapping(value = "/{nombre}/{correo}")
      public ResponseEntity<?> agregarUsuario(@PathVariable("nombre") String nombre, 
        @PathVariable("correo") String correo,
        @RequestHeader("Autorizacion") String token
    ) {
        try{
            autorizacionServicio.autorizar(token);
            salaServicio.agregarUsuario(nombre,correo);
            return ResponseEntity.status(200).body("El usuario: " + correo + " ha sido agregado a la sala");
        }catch( LukaNoAutorizadoException e){
            return ResponseEntity.status(403).body(e.getMessage());        
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @DeleteMapping(value = "/{nombre}/{correo}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable("nombre") String nombre, 
        @PathVariable("correo") String correo,
        @RequestHeader("Autorizacion") String token
    ) {
        try{
            autorizacionServicio.autorizar(token);
            salaServicio.eliminarUsuario(nombre,correo);
            return ResponseEntity.status(200).body("El usuario: " + correo + " ha sido eliminado de la sala");
        }catch(LukaNoAutorizadoException e){
            return ResponseEntity.status(403).body(e.getMessage());        
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PatchMapping(value="/{nombre}")
    public ResponseEntity<?> pujarPorProducto(@PathVariable("nombre") String nombre, 
        @RequestBody Map<String, String> parametros, 
        @RequestHeader("Autorizacion") String token
    ) {
        try{
            autorizacionServicio.autorizar(token);
            double cantidadAPujar = Double.valueOf(parametros.get("cantidadAPujar"));
            String comprador = parametros.get("comprador");
            String idProducto = parametros.get("idProducto");
            salaServicio.pujarPorProducto(nombre, cantidadAPujar, comprador, idProducto);
            return ResponseEntity.status(200).body("Puja realizada");
        }catch(LukaNoAutorizadoException e){
            return ResponseEntity.status(403).body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


    
}
