package edu.arsw.luka.lukaBack.domain;

import java.util.Collection;

import edu.arsw.luka.lukaBack.exception.LukaException;
import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class Sala {

    @NonNull
    private String nombre;

    @NonNull
    private Subasta subasta;


    private Collection<String> compradores;

    private Collection<ElementoSubasta> elementoSubasta;

    
    public void agregarComprador(String comprador) {
        compradores.add(comprador);
    }
    
    public void pujarPorProducto(double cantidadAPujar, Comprador comprador, String idProducto) throws LukaException{
        if(!compradores.contains(comprador.getCorreo())){ 
            throw new LukaException("El comprador no esta en la sala");
        }
        
        if(cantidadAPujar > comprador.getCuentaBancaria().consultarSaldo().doubleValue()){
            throw new LukaException("No tiene fondos suficientes");
        }
        
        for(ElementoSubasta elemento: elementoSubasta){
            if(elemento.getProducto().getIdProducto().equals(idProducto)){
                elemento.realizarPuja(comprador, cantidadAPujar);
            }
        }
    }
}
