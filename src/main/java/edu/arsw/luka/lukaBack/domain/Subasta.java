package edu.arsw.luka.lukaBack.domain;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

import lombok.NonNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Subasta {

    @NonNull
    private final String nombre;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    @NonNull
    private Estado estado;

    @Setter(value = AccessLevel.NONE)
    private Collection<Producto> productos;

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public boolean eliminarProducto(Producto producto) {
        return productos.remove(producto);
    }

    public boolean eliminarProducto(String idProducto) {
        Producto productoEliminar = productos.stream()
                    .filter(producto -> producto.getIdProducto().equals(idProducto))
                    .findFirst().orElse(null);
        return eliminarProducto(productoEliminar);
    }

    public boolean existeProducto(String idProducto) {
        return productos.stream()
                    .anyMatch(producto -> producto.getIdProducto().equals(idProducto));
    }

    public Producto getProducto(String idProducto) {
        return productos.stream()
                    .filter(producto -> producto.getIdProducto().equals(idProducto))
                    .findFirst().orElse(null);
    }
}
