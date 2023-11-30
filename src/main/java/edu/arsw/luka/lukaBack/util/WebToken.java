package edu.arsw.luka.lukaBack.util;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonValue;

import edu.arsw.luka.lukaBack.domain.CuentaBancaria;
import edu.arsw.luka.lukaBack.domain.Usuario;

public class WebToken {

    private JSONObject raiz;

    public WebToken(){

        raiz = new JSONObject();
    }

    public void parseToken(Usuario usuario){
        raiz.put("nombre", usuario.getNombre());
        raiz.put("nombreUsuario", usuario.getNombreUsuario());
        raiz.put("correo", usuario.getCorreo());
        raiz.put("tipoDocumento", usuario.getTipoDocumento());
        raiz.put("numDocumento", usuario.getNumDocumento());
        raiz.put("rol", usuario.getRol());
    }

    public void parseToken (CuentaBancaria cuentaBancaria){
        JSONObject cuenta = new JSONObject();
        cuenta.put("numeroCuenta", cuentaBancaria.getNumeroCuenta());
        cuenta.put("cantidadCredito", cuentaBancaria.consultarSaldo());
        raiz.put("cuentaBancaria", cuenta);
    }

    @Override
    public String toString(){
        System.out.println(raiz.toString());
        return raiz.toString();
    }
    
}
