package edu.arsw.luka.lukaBack.services;

import java.sql.Date;
import java.time.LocalDate;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import edu.arsw.luka.lukaBack.exception.LukaNoAutorizadoException;
import edu.arsw.luka.lukaBack.util.JsonWebToken;
import edu.arsw.luka.lukaBack.util.WebToken;

@Service
public class RedisAutorizacionServicio implements AutorizacionServicio{

    private final JsonWebToken jsonWebToken;
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisAutorizacionServicio(JsonWebToken jsonWebToken, RedisTemplate<String, String> redisTemplate) {
        this.jsonWebToken = jsonWebToken;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean autorizar(String token) throws LukaNoAutorizadoException {
        String [] tokeDecodificado = jsonWebToken.decodificarToken(token);
        JSONObject jsonObject = new JSONObject(tokeDecodificado[1]);
        String tokenRedis = redisTemplate.opsForValue().get(jsonObject.getString("correo"));

        if(tokenRedis == null || !tokenRedis.equals(token)){
            throw new LukaNoAutorizadoException("No autorizado");
        }

        Long fechaExpiracion = Long.valueOf(jsonObject.getLong("exp"));
        if(fechaExpiracion.compareTo(System.currentTimeMillis()/1000) < 0){
            throw new LukaNoAutorizadoException("Token expirado");
        }
        return true;
    }        
    

    @Override
    public String crearToken(WebToken webToken) {
        String token = jsonWebToken.createToken(webToken);        
        JSONObject jsonObject = new JSONObject(webToken.toString());
        redisTemplate.opsForValue().set(jsonObject.getString("correo"),token);
        return token;
    }
    
}
