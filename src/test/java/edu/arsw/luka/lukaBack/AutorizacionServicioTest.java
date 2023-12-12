package edu.arsw.luka.lukaBack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import edu.arsw.luka.lukaBack.domain.Rol;
import edu.arsw.luka.lukaBack.domain.TipoDocumento;
import edu.arsw.luka.lukaBack.domain.Usuario;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.exception.LukaNoAutorizadoException;
import edu.arsw.luka.lukaBack.services.RedisAutorizacionServicio;
import edu.arsw.luka.lukaBack.util.JsonWebToken;
import edu.arsw.luka.lukaBack.util.ValueOperationsAdapter;
import edu.arsw.luka.lukaBack.util.WebToken;

@RunWith(MockitoJUnitRunner.class)
public class AutorizacionServicioTest {

    @Mock
    private JsonWebToken jsonWebToken;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperationsAdapter valueOperationsAdapter;

    @InjectMocks
    private RedisAutorizacionServicio redisAutorizacionServicio;

    private String tokenMock;
    private String tokenExpirado;
    

    public AutorizacionServicioTest() {
        init();
    }

    private void init() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void setUp() {
        //token con fecha de expiracion hasta 2027
        tokenMock = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0MDAiLCJzdWIiOiJFZHVhcmRBcmlhcyIsImlzcyI6Ikx1a2EiLCJpYXQiOjE3MDIwOTQyODUsImV4cCI6MTgwMjA5Nzg4NSwibm9tYnJlIjoiRWR1YXJkIEFyaWFzIiwibm9tYnJlVXN1YXJpbyI6IkVkdWFyZEFyaWFzIiwiY29ycmVvIjoiRWR1YXJkLkFyaWFzQGdtYWlsLmNvbSIsInRpcG9Eb2N1bWVudG8iOiJDQyIsIm51bURvY3VtZW50byI6IjEyMzQwMCIsInJvbCI6IkNPTVBSQURPUiJ9.vmW_lgUhEoMfF9vsbV0kpKI80HIIxplqAJfxmY8neEY";
        tokenExpirado = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0MDAiLCJzdWIiOiJFZHVhcmRBcmlhcyIsImlzcyI6Ikx1a2EiLCJpYXQiOjE3MDIwOTQyODUsImV4cCI6MTcwMjA5Nzg4NSwibm9tYnJlIjoiRWR1YXJkIEFyaWFzIiwibm9tYnJlVXN1YXJpbyI6IkVkdWFyZEFyaWFzIiwiY29ycmVvIjoiRWR1YXJkLkFyaWFzQGdtYWlsLmNvbSIsInRpcG9Eb2N1bWVudG8iOiJDQyIsIm51bURvY3VtZW50byI6IjEyMzQwMCIsInJvbCI6IkNPTVBSQURPUiJ9.LOqDzqNtIoGx9qmXe3S-rmw5B6liWMnHr0uFrPaQ4Eg";
    }

    @Test
    public void deberiaAutorizarToken() throws LukaNoAutorizadoException {
        doCallRealMethod().when(jsonWebToken).decodificarToken(anyString());
        doReturn(tokenMock).when(valueOperationsAdapter).get(anyString());
        doReturn(valueOperationsAdapter).when(redisTemplate).opsForValue();

        var resultadoAutorizacion = redisAutorizacionServicio.autorizar(tokenMock);
        assertTrue(resultadoAutorizacion);

    }

    @Test
    public void noDeberiaAutorizarSiElTokenEsInvalido() throws Exception {
        doCallRealMethod().when(jsonWebToken).decodificarToken(anyString());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> redisAutorizacionServicio.autorizar(""));
        assertThrows(NullPointerException.class, () -> redisAutorizacionServicio.autorizar(null));
        assertThrows(IllegalArgumentException.class, () -> redisAutorizacionServicio.autorizar("hola.mundo.adios"));
    }
    
    @Test
    public void noDeberiaAutorizarSiNoExisteTokenAlmacenado() throws LukaException {
        doCallRealMethod().when(jsonWebToken).decodificarToken(anyString());
        doReturn(valueOperationsAdapter).when(redisTemplate).opsForValue();
        doReturn(null)
            .doReturn("no soy el token almacenado")
            .when(valueOperationsAdapter).get(anyString());

        String tokenNoAlmacenado = tokenMock;
        String tokenNoCorrespondiente = tokenMock;
        assertThrows(LukaNoAutorizadoException.class, () -> redisAutorizacionServicio.autorizar(tokenNoAlmacenado));
        assertThrows(LukaNoAutorizadoException.class, () -> redisAutorizacionServicio.autorizar(tokenNoCorrespondiente));
        
    }

    @Test
    public void noDeberiaAutorizarSiElTokenEstaExpirado() throws LukaException {
        doCallRealMethod().when(jsonWebToken).decodificarToken(anyString());
        doReturn(tokenExpirado).when(valueOperationsAdapter).get(anyString());
        doReturn(valueOperationsAdapter).when(redisTemplate).opsForValue();

        assertThrows(LukaNoAutorizadoException.class, () -> redisAutorizacionServicio.autorizar(tokenExpirado));
    }

    @Test
    public void deberiaCrearToken() throws LukaException {
        doReturn(tokenMock).when(jsonWebToken).createToken(any(WebToken.class));
        doReturn(valueOperationsAdapter).when(redisTemplate).opsForValue();
        doNothing().when(valueOperationsAdapter).set(anyString(), anyString());

        WebToken webToken = new WebToken();
        webToken.parseToken(new Usuario(
            "luisa",
            "daniela",
            "luisa.daniela@gmail.com",
            TipoDocumento.CC,
            "123400",
            "password",
            Rol.COMPRADOR
        ));
        var tokenCreado = redisAutorizacionServicio.crearToken(webToken);

        assertNotNull(tokenCreado);
        assertEquals(tokenMock, tokenCreado);
    }

    @Test
    public void noDeberiaCrearTokenSiElUsuarioNoEsValido() {
        assertThrows(JSONException.class, () -> redisAutorizacionServicio.crearToken(new WebToken()));
        assertThrows(NullPointerException.class, () -> redisAutorizacionServicio.crearToken(null));
        
    }

    @Test
    public void noDeberiaCrearTokenSiHayErrorEnAlmacenamiento() {
        doReturn(tokenMock).when(jsonWebToken).createToken(any(WebToken.class));
        doReturn(valueOperationsAdapter).when(redisTemplate).opsForValue();
        doThrow(RuntimeException.class).when(valueOperationsAdapter).set(anyString(), anyString());

        WebToken webToken = new WebToken();
        webToken.parseToken(new Usuario(
            "luisa",
            "daniela",
            "luisa.daniela@gmail.com",
            TipoDocumento.CC,
            "123400",
            "password",
            Rol.COMPRADOR
        ));

        assertThrows(RuntimeException.class, () -> redisAutorizacionServicio.crearToken(webToken));
    }
    
}
