package edu.arsw.luka.lukaBack.services;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'autorizar'");
    }

    @Override
    public String crearToken(WebToken webToken) {
        var token = jsonWebToken.createToken(webToken);
        JSONObject jsonObject = new JSONObject(webToken.toString());
        redisTemplate.opsForValue().set(jsonObject.getString("correo"),token);
        return jsonWebToken.createToken(webToken);
    }
    
}
