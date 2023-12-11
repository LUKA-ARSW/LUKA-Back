package edu.arsw.luka.lukaBack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import java.util.HashMap;
import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.util.Pair;

import edu.arsw.luka.lukaBack.domain.CuentaBancaria;
import edu.arsw.luka.lukaBack.domain.Rol;
import edu.arsw.luka.lukaBack.domain.TipoDocumento;
import edu.arsw.luka.lukaBack.domain.Usuario;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.exception.LukaLoginException;
import edu.arsw.luka.lukaBack.persistence.repositorio.UsuarioRepositorio;
import edu.arsw.luka.lukaBack.services.AutorizacionServicio;
import edu.arsw.luka.lukaBack.services.UsuarioServicioImpl;
import edu.arsw.luka.lukaBack.util.WebToken;

import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServicioTest {


    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @Spy
    private AutorizacionServicio autorizacionServicio;

    @InjectMocks
    private UsuarioServicioImpl usuarioServicio;

    private Map<String,String> usuarioCorrectoMap; 
    private Usuario usuarioCorrecto;
    private CuentaBancaria cuentaBancariaCorrecta;


    public UsuarioServicioTest() {
        init();
    }

    public void init(){
        MockitoAnnotations.openMocks(this);
    }


    @BeforeEach
    public void setUp() {
        usuarioCorrectoMap = new HashMap<String,String>();
        usuarioCorrectoMap.put("nombre", "Estella Giron");
        usuarioCorrectoMap.put("nombreUsuario", "EstellaGiron");
        usuarioCorrectoMap.put("correo", "EstellaGiron@gmail.com");
        usuarioCorrectoMap.put("tipoDocumento", "CC");
        usuarioCorrectoMap.put("numDocumento", "40377232");
        usuarioCorrectoMap.put("contrasena", "estella-123");
        usuarioCorrectoMap.put("rol", "AMBOS");

        usuarioCorrecto = new Usuario(
            "Estella Giron",
            "EstellaGiron",
            "EstellaGiron@gmail.com",
            TipoDocumento.valueOf("CC"),
            "40377232",
            "estella-123",
            Rol.valueOf("AMBOS")

        );

        cuentaBancariaCorrecta = new CuentaBancaria("1111111111", 10000000);
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
        assertThrows(NullPointerException.class, () -> usuarioServicio.crearUsuario(null));
        assertThrows(NullPointerException.class, () -> usuarioServicio.crearUsuario(new HashMap<String,String>()));

    }

    @Test
    public void deberiaLanzarErrorSiTipoDocNoCorresponde(){
        var nuevoUsuarioMap = new HashMap<String,String>(usuarioCorrectoMap);

        nuevoUsuarioMap.put("tipoDocumento", "CC2");
        assertThrows(IllegalArgumentException.class, () -> usuarioServicio.crearUsuario(nuevoUsuarioMap));
        
        nuevoUsuarioMap.put("tipoDocumento", null);
        assertThrows(NullPointerException.class, () -> usuarioServicio.crearUsuario(nuevoUsuarioMap));

    }

    @Test
    public void deberiaLanzarErrorSiRolNoCorresponde(){
        var nuevoUsuarioMap = new HashMap<String,String>(usuarioCorrectoMap);

        nuevoUsuarioMap.put("rol", "CC2");
        assertThrows(IllegalArgumentException.class, () -> usuarioServicio.crearUsuario(nuevoUsuarioMap));
        
        nuevoUsuarioMap.put("rol", null);
        assertThrows(NullPointerException.class, () -> usuarioServicio.crearUsuario(nuevoUsuarioMap));

    }

    @Test
    public void deberiaPropagarExcepcionDeRepositorio() throws LukaException{
        doThrow(LukaException.class).when(usuarioRepositorio).crearUsuario(any(Usuario.class));
        assertThrows(LukaException.class, ()->usuarioServicio.crearUsuario(usuarioCorrectoMap));
    }

    @Test
    public void deberiaHacerLogin() throws LukaException {
        var cuentaMock = Pair.of(usuarioCorrecto, cuentaBancariaCorrecta);
        var resultadoDeseado = "SoyUnJWT";

        doReturn(cuentaMock).when(usuarioRepositorio).login(any(String.class), any(String.class));
        doReturn(resultadoDeseado).when(autorizacionServicio).crearToken(any(WebToken.class));


        var resultadoLogin = usuarioServicio.login("username", "password");
        assertNotNull(resultadoLogin);
        assertEquals(resultadoDeseado, resultadoLogin);

    }

    @Test
    public void noDeberiaHacerLoginSiElUsuarioOContrasenaSonIncorrectos() throws LukaException {
        var answer = new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String username = invocation.getArgument(0);
                String password = invocation.getArgument(1);

                if (!username.equals("EstellaGiron") || !password.equals("estella-123")) {
                    throw new LukaLoginException("El usuario o contraseña no son validos");
                }
                return null;
            }
        };

        doAnswer(answer).when(usuarioRepositorio).login(anyString(), anyString());
        assertThrows(LukaLoginException.class, () -> usuarioServicio.login("EstellaGiron", "password"));
        assertThrows(LukaLoginException.class, () -> usuarioServicio.login("username", "estella-123"));

    }

    @Test
    public void noDeberiaHacerLoginSiElUsuarioOContrasenaSonNulos() throws LukaException {
        assertThrows(NullPointerException.class, () -> usuarioServicio.login(null, "password"));
        assertThrows(NullPointerException.class, () -> usuarioServicio.login("username", null));
        assertThrows(NullPointerException.class, () -> usuarioServicio.login(null, null));
    }


    @Test
    public void deberiaHacerLogout() throws LukaException {
        doNothing().when(autorizacionServicio).logout(anyString());
        var resultado = usuarioServicio.logout("token");

        assertNotNull(resultado);
        assertEquals("Deslogueo exitoso", resultado);
    }

    @Test
    public void noDeberiaHacerLogoutSiElTokenEsNulo() throws LukaException {
        doThrow(NullPointerException.class).when(autorizacionServicio).logout(null);
        assertThrows(NullPointerException.class, () -> usuarioServicio.logout(null));
    }

    @Test
    public void noDeberiaHacerLogoutSiElTokenNoEsValido() throws LukaException {
        var answer = new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String token = invocation.getArgument(0);
                Base64.Decoder decoder = Base64.getUrlDecoder();

                String[] partes = token.split("\\.");


                return decoder.decode(partes[2]);
            }
        };
        
        doAnswer(answer).when(autorizacionServicio).logout(anyString());

        assertThrows(IllegalArgumentException.class, () -> usuarioServicio.logout("j.w.t"));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> usuarioServicio.logout("jwt"));
    }


    
}
