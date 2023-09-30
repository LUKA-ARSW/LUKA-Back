package edu.arsw.luka.lukaBack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.arsw.luka.lukaBack.domain.Producto;
import edu.arsw.luka.lukaBack.services.ProductoServicio;

@RestController
@RequestMapping(value = "/producto")
public class ProductoController {

   private ProductoServicio productoServicio;

   @Autowired
   public ProductoController(ProductoServicio productoServicio) {
       this.productoServicio = productoServicio;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> agregarProducto(@RequestBody Producto producto) {
        try{
            productoServicio.agregarProducto(producto);
            return ResponseEntity.status(201).body("Producto :" + producto.getNombre() +" agregado");
        }catch(Exception e){
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
    
}
