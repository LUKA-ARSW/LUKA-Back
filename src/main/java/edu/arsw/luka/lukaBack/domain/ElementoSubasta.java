package edu.arsw.luka.lukaBack.domain;

import java.util.Collection;
import java.util.PriorityQueue;

import org.springframework.data.util.Pair;

import com.mongodb.lang.NonNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Builder.Default;

@Getter
@Builder
public class ElementoSubasta {
    @NonNull
    private Producto producto;

    @NonNull
    private Double pujaMaxima;

    @Default
    private PriorityQueue<Pair<Comprador,Double>> compradores = new PriorityQueue<>(
        (p1,p2) -> p1.getSecond().compareTo(p2.getSecond())>0?-1:1
    );
    
    public void agregarComprador(Comprador comprador, Double puja) {
        compradores.add(Pair.of(comprador, puja));
    }

    public void agregarComprador(Collection<Pair<Comprador,Double>> comprador) {
        compradores.addAll(comprador);
    }

}
