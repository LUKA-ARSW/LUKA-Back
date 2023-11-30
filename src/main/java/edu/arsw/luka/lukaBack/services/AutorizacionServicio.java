package edu.arsw.luka.lukaBack.services;

import org.springframework.stereotype.Service;

import edu.arsw.luka.lukaBack.exception.LukaNoAutorizadoException;
import edu.arsw.luka.lukaBack.util.WebToken;


public interface AutorizacionServicio {
    public boolean autorizar(String token) throws LukaNoAutorizadoException;
    
    public String crearToken(WebToken webToken);
}
