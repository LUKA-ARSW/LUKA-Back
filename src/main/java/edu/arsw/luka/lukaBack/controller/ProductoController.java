package edu.arsw.luka.lukaBack.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import edu.arsw.luka.lukaBack.domain.Categoria;
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
            return ResponseEntity.status(201).body("Producto :" + producto.getNombre() +" ha sido agregado");
        }catch(Exception e){
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getTodosLosProductos() {
        try{
            var result = productoServicio.consultarTodosLosProductos();
            return ResponseEntity.status(200).body(result);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping(value = "/id/{idProducto}")
    public ResponseEntity<?> getProductoPorIdProducto(@PathVariable("idProducto") String idProducto) {
        try{
            var result = productoServicio.consultarProductoPorIdProducto(idProducto);
            return ResponseEntity.status(200).body(result);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping(value = "/nombre/{nombre}")
    public ResponseEntity<?> getProductoPorNombre(@PathVariable("nombre") String nombre ) {
        try{
            var result = productoServicio.consultarProductoPorNombre(nombre);
            return ResponseEntity.status(200).body(result);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{idProducto}")
    public ResponseEntity<?> modificarProducto(@PathVariable("idProducto") String idProducto, @RequestBody Producto producto) {
            try{
                productoServicio.modificarProducto(idProducto, producto);
                return ResponseEntity.status(201).body("Producto: " + idProducto + " ha sido modificado");
            }catch(Exception e){
                return ResponseEntity.status(403).body(e.getMessage());
            }
    }

    @DeleteMapping(value = "/{idProducto}")
    public ResponseEntity<?> eliminarProducto(@PathVariable("idProducto") String idProducto) {
        try{
            productoServicio.eliminarProducto(idProducto);
            return ResponseEntity.status(200).body("El producto: " + idProducto + " ha sido eliminado");
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    
}
