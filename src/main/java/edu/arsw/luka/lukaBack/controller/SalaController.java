package edu.arsw.luka.lukaBack.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.arsw.luka.lukaBack.domain.Sala;
import edu.arsw.luka.lukaBack.domain.Subasta;
import edu.arsw.luka.lukaBack.services.SalaServicio;
import edu.arsw.luka.lukaBack.services.SubastaServicio;

@RestController
@RequestMapping(value = "/sala")
public class SalaController {

    private SalaServicio salaServicio;

    @Autowired
    public SalaController(SalaServicio salaServicio){
        this.salaServicio = salaServicio;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> crearSala(@RequestBody Sala sala) {
        try{
            salaServicio.agregarSala(sala);
            return ResponseEntity.status(201).body("Sala :" + sala.getNombre() +" creada");

        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getTodasLasSalas() {
        try{
            var result=salaServicio.consultarTodasLasSalas();
            return ResponseEntity.status(200).body(result);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping(value = "/{nombre}")
    public ResponseEntity<?> getSalaPorNombre(@PathVariable(required =true, value ="nombre") String nombre) {  
        try{
            var result= salaServicio.consultarSalaPorNombre(nombre);
            return ResponseEntity.status(200).body(result);

        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    
    @DeleteMapping(value = "/{nombre}")
    public ResponseEntity<?> eliminarSala(@PathVariable("nombre") String nombre) {
        try{
            salaServicio.eliminarSala(nombre);
            return ResponseEntity.status(200).body("La sala: " + nombre + " ha sido eliminado");
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping(value = "/{nombre}/{correo}")
      public ResponseEntity<?> agregarUsuario(@PathVariable("nombre") String nombre, @PathVariable("correo") String correo) {
        try{
            salaServicio.agregarUsuario(nombre,correo);
            return ResponseEntity.status(200).body("El usuario: " + correo + " ha sido agregado a la sala");
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{nombre}/{correo}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable("nombre") String nombre, @PathVariable("correo") String correo) {
        try{
            salaServicio.eliminarUsuario(nombre,correo);
            return ResponseEntity.status(200).body("El usuario: " + correo + " ha sido eliminado de la sala");
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PatchMapping(value="/{nombre}")
    public ResponseEntity<?> pujarPorProducto(@PathVariable("nombre") String nombre, @RequestBody Map<String, String> parametros) {
        try{
            double cantidadAPujar = Double.valueOf(parametros.get("cantidadAPujar"));
            String comprador = parametros.get("comprador");
            String idProducto = parametros.get("idProducto");
            salaServicio.pujarPorProducto(nombre, cantidadAPujar, comprador, idProducto);
            return ResponseEntity.status(200).body("Puja realizada");
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


    
}
