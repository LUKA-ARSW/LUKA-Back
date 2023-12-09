package edu.arsw.luka.lukaBack.util;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.bson.json.JsonObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonSerializable.Base;

import io.jsonwebtoken.JwtParser;
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

    public String[] decodificarToken(String token){
        String [] partes = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        return new String[]{
            new String(decoder.decode(partes[0])),
            new String(decoder.decode(partes[1])),
            new String(decoder.decode(partes[2]))
        };
    }
    
}
