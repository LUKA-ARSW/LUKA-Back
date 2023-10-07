package edu.arsw.luka.lukaBack.domain;

import java.awt.Image;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @NonNull
    private String idProducto;

    @NonNull
    private String nombre;

    private String descripcion;

    @NonNull
    private String foto;

    @NonNull
    private Double precio;

    @NonNull
    private Categoria categoria;

    
}
