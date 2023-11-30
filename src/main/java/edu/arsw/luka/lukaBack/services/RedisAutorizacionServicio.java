package edu.arsw.luka.lukaBack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.arsw.luka.lukaBack.exception.LukaNoAutorizadoException;
import edu.arsw.luka.lukaBack.util.JsonWebToken;
import edu.arsw.luka.lukaBack.util.WebToken;

@Service
public class RedisAutorizacionServicio implements AutorizacionServicio{

    private final JsonWebToken jsonWebToken;

    @Autowired
    public RedisAutorizacionServicio(JsonWebToken jsonWebToken) {
        this.jsonWebToken = jsonWebToken;
    }

    @Override
    public boolean autorizar(String token) throws LukaNoAutorizadoException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'autorizar'");
    }

    @Override
    public String crearToken(WebToken webToken) {
        return jsonWebToken.createToken(webToken);
    }
    
}
