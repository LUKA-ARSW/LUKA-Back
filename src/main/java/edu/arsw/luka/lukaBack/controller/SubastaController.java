package edu.arsw.luka.lukaBack.controller;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.arsw.luka.lukaBack.domain.Subasta;

@RestController
@RequestMapping(value = "/subasta")
public class SubastaController {

    @PostMapping(value = "")
    public ResponseEntity<?> crearSubasta(@RequestBody Subasta subasta) {
        try{
            return ResponseEntity.status(201).body("Subasta :" + subasta.getNombre() +" creada");
        }catch(Exception e){
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getTodasLasSubastas() {
        try{
            return ResponseEntity.status(200).body("Subastas Obtenidas");
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping(value = "/{nombre}")
    public ResponseEntity<?> getSubastaPorNombre(@PathVariable("nombre") String nombre) {
        try{
            return ResponseEntity.status(200).body("Subasta: " + nombre);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{nombre}")
    public ResponseEntity<?> modificarFechaInicioSubasta(@RequestParam("fechaInicio") LocalDateTime fechaInicio, @RequestParam("fechaFin") LocalDateTime fechaFin) {
        try{
            return ResponseEntity.status(201).body("Fechas de la subasta actualizadas");
        }catch(Exception e){
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{nombre}")
    public ResponseEntity<?> eliminarSubastabyId(@PathVariable("nombre") String nombre) {
        try{
            return ResponseEntity.status(200).body("La subasta: " + nombre + " eliminada");
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}