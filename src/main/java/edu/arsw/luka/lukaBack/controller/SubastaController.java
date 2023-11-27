package edu.arsw.luka.lukaBack.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.arsw.luka.lukaBack.domain.Estado;
import edu.arsw.luka.lukaBack.domain.Producto;
import edu.arsw.luka.lukaBack.domain.Subasta;
import edu.arsw.luka.lukaBack.domain.TipoSubasta;
import edu.arsw.luka.lukaBack.services.SubastaServicio;
import lombok.experimental.var;

@RestController
@RequestMapping(value = "/subasta")
public class SubastaController {

    private SubastaServicio subastaServicio;

    @Autowired
    public SubastaController(SubastaServicio subastaServicio) {
        this.subastaServicio = subastaServicio;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> crearSubasta(@RequestBody Subasta subasta) {
        try{
            subastaServicio.agregarSubasta(subasta);
            return ResponseEntity.status(201).body("Subasta :" + subasta.getNombre() +" creada");
        }catch(Exception e){
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getTodasLasSubastas() {
        try{
            var result=subastaServicio.consultarTodasLasSubastas();
            return ResponseEntity.status(200).body(result);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping(value = "/{nombre}")
    public ResponseEntity<?> getSubastaPorNombre(@PathVariable(required =true, value ="nombre") String nombre) {
        try{
            var result= subastaServicio.consultarSubastaPorNombre(nombre);
            return ResponseEntity.status(200).body(result);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping(value="/tipo/{tipo}}")
    public ResponseEntity<?> getSubastaPorTipo(@PathVariable(required =true, value ="tipo") TipoSubasta tipo) {
        try{
            var result= subastaServicio.consultarSubastaPorTipo(tipo);
            return ResponseEntity.status(200).body(result);

        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PatchMapping(value = "/{nombre}/fecha")
    public ResponseEntity<?> modificarFechaSubasta(
        @PathVariable("nombre") String nombre,
        @RequestParam(required = false, value="fechaInicio") LocalDateTime fechaInicio, 
        @RequestParam(required = false, value="fechaFin") LocalDateTime fechaFin, 
        @RequestParam(required = false, value="cambiarTipo") Boolean cambiarTipo
    ) {
        try{
            subastaServicio.modificarFechaSubasta(nombre, fechaInicio, fechaFin, cambiarTipo.booleanValue());
            return ResponseEntity.status(201).body("Fechas de la subasta actualizadas");
        }catch(Exception e){
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

   @PatchMapping(value = "/{nombre}/estado")
   public ResponseEntity<?> modificarEstadoSubasta(@PathVariable(value="nombre") String nombre) {
       try{
           subastaServicio.modificarEstadoSubasta(nombre);
           return ResponseEntity.status(201).body("Estado de la subasta actualizado");
       }catch(Exception e){
           return ResponseEntity.status(403).body(e.getMessage());
       }
    }

    @DeleteMapping(value = "/{nombre}")
    public ResponseEntity<?> eliminarSubastabyId(@PathVariable("nombre") String nombre) {
        try{
            subastaServicio.eliminarSubasta(nombre);
            return ResponseEntity.status(200).body("La subasta: " + nombre + " eliminada");
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PatchMapping(value = "/{nombre}/producto")
    public ResponseEntity<?> agregarProductoSubasta(@PathVariable("nombre") String nombre, @RequestBody Producto producto) {
        try{
            subastaServicio.agregarProductoSubasta(nombre, producto);
            return ResponseEntity.status(200).body("el producto ha sido agregado a la subasta: " + nombre);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());

        }
    }

    @DeleteMapping(value = "/{nombre}/producto/{idProducto}")
    public ResponseEntity<?> eliminarProductoSubasta(@PathVariable("nombre") String nombre, @PathVariable("idProducto") String idProducto) {
        try{
            subastaServicio.eliminarProductoSubasta(nombre, idProducto);
            return ResponseEntity.status(200).body("el producto ha sido eliminado de la subasta: " + nombre);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
