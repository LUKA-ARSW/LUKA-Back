package edu.arsw.luka.lukaBack.domain.entity;

import java.util.List;

import org.springframework.data.util.Pair;

import edu.arsw.luka.lukaBack.domain.Comprador;

public class ElementoSubastaEntidad {

    private String idProducto;

    private Double pujaMaxima;

    private List<Pair<Comprador,Double>> compradores;


    
}
