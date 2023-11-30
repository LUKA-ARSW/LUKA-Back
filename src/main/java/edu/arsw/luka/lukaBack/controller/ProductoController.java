package edu.arsw.luka.lukaBack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.arsw.luka.lukaBack.domain.Categoria;
import edu.arsw.luka.lukaBack.domain.Producto;
import edu.arsw.luka.lukaBack.exception.LukaNoAutorizadoException;
import edu.arsw.luka.lukaBack.services.AutorizacionServicio;
import edu.arsw.luka.lukaBack.services.ProductoServicio;

@RestController
@RequestMapping(value = "/producto")
public class ProductoController {
    private ProductoServicio productoServicio;
    private AutorizacionServicio autorizacionServicio;
    
    @Autowired
    public ProductoController(ProductoServicio productoServicio , AutorizacionServicio autorizacionServicio) {
        this.productoServicio = productoServicio;
        this.autorizacionServicio = autorizacionServicio;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> agregarProducto(@RequestBody Producto producto, 
        @RequestHeader("Autorizacion") String token
    ) {
        try{
            autorizacionServicio.autorizar(token);
            productoServicio.agregarProducto(producto);
            return ResponseEntity.status(201).body("Producto :" + producto.getNombre() +" ha sido agregado");
        }catch(LukaNoAutorizadoException e){
            return ResponseEntity.status(403).body(e.getMessage());
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getTodosLosProductos(@RequestHeader("Autorizacion") String token) {
        try{
            autorizacionServicio.autorizar(token);
            var result = productoServicio.consultarTodosLosProductos();
            return ResponseEntity.status(200).body(result);
        }catch(LukaNoAutorizadoException e){
            return ResponseEntity.status(403).body(e.getMessage());

        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @GetMapping(value = "/id/{idProducto}")
    public ResponseEntity<?> getProductoPorIdProducto(@PathVariable("idProducto") String idProducto, 
    @RequestHeader("Autorizacion") String token
    ) {
        try{
            autorizacionServicio.autorizar(token);
            var result = productoServicio.consultarProductoPorIdProducto(idProducto);
            return ResponseEntity.status(200).body(result);
        }catch(LukaNoAutorizadoException e){
            return ResponseEntity.status(403).body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping(value = "/nombre/{nombre}")
    public ResponseEntity<?> getProductoPorNombre(@PathVariable("nombre") String nombre, 
    @RequestHeader("Autorizacion") String token
    ) {
        try{
            autorizacionServicio.autorizar(token);
            var result = productoServicio.consultarProductoPorNombre(nombre);
            return ResponseEntity.status(200).body(result);
        }catch(LukaNoAutorizadoException e){
            return ResponseEntity.status(403).body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{idProducto}")
    public ResponseEntity<?> modificarProducto(@PathVariable("idProducto") String idProducto, 
        @RequestBody Producto producto, 
        @RequestHeader("Autorizacion") String token
    ) {
            try{
                autorizacionServicio.autorizar(token);
                productoServicio.modificarProducto(idProducto, producto);
                return ResponseEntity.status(201).body("Producto: " + idProducto + " ha sido modificado");
            }catch(LukaNoAutorizadoException e){
                return ResponseEntity.status(403).body(e.getMessage());
            }catch(Exception ex){
                return ResponseEntity.status(403).body(ex.getMessage());
            }
    }

    @DeleteMapping(value = "/{idProducto}")
    public ResponseEntity<?> eliminarProducto(@PathVariable("idProducto") String idProducto,
        @RequestHeader("Autorizacion") String token
    ) {
        try{
            autorizacionServicio.autorizar(token);
            productoServicio.eliminarProducto(idProducto);
            return ResponseEntity.status(200).body("El producto: " + idProducto + " ha sido eliminado");
        }catch(LukaNoAutorizadoException e){
            return ResponseEntity.status(403).body(e.getMessage());
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }
    
}
