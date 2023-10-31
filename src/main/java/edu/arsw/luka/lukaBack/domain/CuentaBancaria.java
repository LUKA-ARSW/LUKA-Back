package edu.arsw.luka.lukaBack.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.NonNull;

import edu.arsw.luka.lukaBack.exception.LukaException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CuentaBancaria {

    @NonNull
    @JsonProperty("numeroCuenta")
    private String numeroCuenta;

    @NonNull
    @JsonProperty("cantidadCredito")
    private BigDecimal cantidadCredito;

    public CuentaBancaria(String numeroCuenta) {
        this(numeroCuenta,0);
    }

    public CuentaBancaria(String numeroCuenta, double cantidadCredito) {
        this.numeroCuenta = numeroCuenta;
        this.cantidadCredito = BigDecimal.valueOf(cantidadCredito);
    }

    public BigDecimal agregarFondos(BigDecimal cantidad){
        this.cantidadCredito = this.cantidadCredito.add(cantidad);
        return cantidadCredito;
    }

    public BigDecimal quitarFondos(BigDecimal cantidad){
        this.cantidadCredito = this.cantidadCredito.subtract(cantidad);
        return cantidadCredito;
    }

    public BigDecimal quitarFondos(double cantidad){
        return this.quitarFondos(BigDecimal.valueOf(cantidad));
    }

    public void transferirFondos(CuentaBancaria cuentaDestino, BigDecimal cantidad) throws LukaException{
        if(this.cantidadCredito.compareTo(cantidad) < 0){
            throw new LukaException("No hay fondos suficientes");
        }
        this.quitarFondos(cantidad);
        cuentaDestino.agregarFondos(cantidad);
    }

    public BigDecimal consultarSaldo(){
        return this.cantidadCredito;
    }
    
}
