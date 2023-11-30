package edu.arsw.luka.lukaBack.util;

import java.security.Key;
import java.util.Date;

import org.bson.json.JsonObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JsonWebToken {
    

    @Value("${Luka.jwt.secret}")
    private String secretKey;

    @Value("${Luka.jwt.expiration}")
    private int tiempoExpiracion;

    public String createToken(WebToken webToken) {
        JSONObject infoUsuario = new JSONObject(webToken.toString());
        return Jwts.builder()
            .id(infoUsuario.getString("numDocumento"))
            .subject(infoUsuario.getString("nombreUsuario"))
            .issuer("Luka")
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + tiempoExpiracion))
            .claim("nombre", infoUsuario.getString("nombre"))
            .claim("nombreUsuario", infoUsuario.getString("nombreUsuario"))
            .claim("correo", infoUsuario.getString("correo"))
            .claim("tipoDocumento", infoUsuario.getString("tipoDocumento"))
            .claim("numDocumento", infoUsuario.getString("numDocumento"))
            .claim("rol", infoUsuario.getString("rol"))
            .claim("numeroCuenta", infoUsuario.opt("numeroCuenta"))
            .claim("cantidadCredito", infoUsuario.opt("cantidadCredito"))
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
            .compact();
    }
    
}
