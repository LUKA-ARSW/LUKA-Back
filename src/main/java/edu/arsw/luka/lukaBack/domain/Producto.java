package edu.arsw.luka.lukaBack.domain;

import java.awt.Image;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Getter;


@Getter
@Setter
@RequiredArgsConstructor
public class Producto {

    @NonNull
    private String nombre;

    private String descripcion;

    @NonNull
    private Image foto;

    @NonNull
    private Double precio;

    @NonNull
    private Categoria categoria;

    
}
