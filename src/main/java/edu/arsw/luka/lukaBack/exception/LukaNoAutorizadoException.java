package edu.arsw.luka.lukaBack.exception;

public class LukaNoAutorizadoException extends LukaException {

    public LukaNoAutorizadoException() {
        super("No autorizado");
    }

    public LukaNoAutorizadoException(String message) {
        super(message);
    }
    
}
