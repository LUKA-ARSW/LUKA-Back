package edu.arsw.luka.lukaBack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Answers.valueOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.yaml.snakeyaml.error.YAMLException;

import edu.arsw.luka.lukaBack.domain.Rol;
import edu.arsw.luka.lukaBack.domain.TipoDocumento;
import edu.arsw.luka.lukaBack.domain.Usuario;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.persistence.repositorio.UsuarioRepositorio;
import edu.arsw.luka.lukaBack.services.UsuarioServicio;
import edu.arsw.luka.lukaBack.services.UsuarioServicioImpl;
import junit.framework.TestCase;

import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServicioTest extends TestCase{


    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @InjectMocks
    private UsuarioServicioImpl usuarioServicio;

    private Map<String,String> usuarioCorrectoMap; 

    private Usuario usuarioCorrecto;

    public UsuarioServicioTest(){
        initMock();
        
    }

    public void initMock(){
        MockitoAnnotations.initMocks(this);

    }

    @Before
    public void setUp() {
        usuarioCorrectoMap = new HashMap<String,String>();
        usuarioCorrectoMap.put("nombre", "Estella Giron");
        usuarioCorrectoMap.put("nombreUsuario", "EstellaGiron");
        usuarioCorrectoMap.put("correo", "EstellaGiron@gmail.com");
        usuarioCorrectoMap.put("tipoDocumento", "CC");
        usuarioCorrectoMap.put("numDocumento", "40377232");
        usuarioCorrectoMap.put("contrasena", "estella-123");
        usuarioCorrectoMap.put("rol", "AMBAS");

        usuarioCorrecto = new Usuario(
            "Estella Giron",
            "EstellaGiron",
            "EstellaGiron@gmail.com",
            TipoDocumento.valueOf("CC"),
            "40377232",
            "estella-123",
            Rol.valueOf("AMBOS")

        );
    }



    @Test
    public void deberiaCrearUsuario() throws LukaException{
        doReturn(usuarioCorrecto).when(usuarioRepositorio).crearUsuario(any(Usuario.class));
        Usuario usuarioRespuesta = usuarioServicio.crearUsuario(usuarioCorrectoMap); 
        assertNotNull(usuarioRespuesta);
        assertEquals(usuarioCorrecto, usuarioRespuesta);     
    }

    @Test
    public void deberiaLanzarExcepcionSiNoTieneInfoUsuario(){
        assertThrows(NullPointerException.class, () -> {
            usuarioServicio.crearUsuario(null);
        });

        assertThrows(NullPointerException.class, () -> {
            usuarioServicio.crearUsuario(new HashMap<String,String>());
        });

    }

    @Test
    public void deberiaLanzarErrorSiTipoDocNoCorresponde(){
        var nuevoUsuarioMap = new HashMap<String,String>(usuarioCorrectoMap);
        nuevoUsuarioMap.put("tipoDocumento", "CC2");
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioServicio.crearUsuario(nuevoUsuarioMap);
        });
        
        nuevoUsuarioMap.put("tipoDocumento", null);
        assertThrows(NullPointerException.class, () -> {
            usuarioServicio.crearUsuario(nuevoUsuarioMap);
        });

    }

        @Test
    public void deberiaLanzarErrorSiRolNoCorresponde(){
        var nuevoUsuarioMap = new HashMap<String,String>(usuarioCorrectoMap);
        nuevoUsuarioMap.put("rol", "CC2");
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioServicio.crearUsuario(nuevoUsuarioMap);
        });
        
        nuevoUsuarioMap.put("rol", null);
        assertThrows(NullPointerException.class, () -> {
            usuarioServicio.crearUsuario(nuevoUsuarioMap);
        });

    }

    @Test
    public void deberiaPropagarExcepcionDeRepositorio() throws LukaException{
        doThrow(LukaException.class).when(usuarioRepositorio).crearUsuario(any(Usuario.class));

        assertThrows(LukaException.class, ()->usuarioServicio.crearUsuario(usuarioCorrectoMap));
    }


    
}
