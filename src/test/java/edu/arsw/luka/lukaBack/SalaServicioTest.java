package edu.arsw.luka.lukaBack;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import edu.arsw.luka.lukaBack.domain.Categoria;
import edu.arsw.luka.lukaBack.domain.ElementoSubasta;
import edu.arsw.luka.lukaBack.domain.Estado;
import edu.arsw.luka.lukaBack.domain.Producto;
import edu.arsw.luka.lukaBack.domain.Sala;
import edu.arsw.luka.lukaBack.domain.Subasta;
import edu.arsw.luka.lukaBack.domain.TipoSubasta;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.persistence.repositorio.SalaRepositorio;
import edu.arsw.luka.lukaBack.services.SalaServicioImpl;

@RunWith(MockitoJUnitRunner.class)
public class SalaServicioTest {

    @Mock
    private SalaRepositorio salaRepositorio;

    @InjectMocks
    private SalaServicioImpl salaServicio;

    private Sala salaMock;
    private Subasta subastaMock;
    private Producto productoMock;

    public SalaServicioTest() {
        init();
    }

    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setUp(){
        subastaMock = new Subasta(
            "SubastaPrueba",
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(1),
            TipoSubasta.LARGA,
            Estado.EN_CURSO,
            new ArrayList<>()
        );

        salaMock = new Sala(
            "Sala01",
            subastaMock,
            new ArrayList<>(),
            new ArrayList<>()
        );

        productoMock = new Producto(
            "Producto01",
            "ProductoPrueba",
            "DescripcionPrueba",
            "Foto producto prueba",
            1000.0,
            Categoria.ELECTRODOMESTICOS,
            "VendedorPrueba"
        );
    }

    @Test
    public void deberiaAgregarSala() throws LukaException {
        doReturn(salaMock)
            .doThrow(LukaException.class) 
            .when(salaRepositorio).agregarSala(nullable(Sala.class));

        var resultado = salaServicio.agregarSala(salaMock);
        assertNotNull(resultado);

        assertThrows(LukaException.class, () -> salaServicio.agregarSala(salaMock));

    }

    @Test
    public void deberiaConsultarTodasLasSalas(){
        doReturn(new ArrayList<Sala>())
            .doReturn(List.of(salaMock))
            .when(salaRepositorio).consultarTodasLasSalas();

        var resultado = salaServicio.consultarTodasLasSalas();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        resultado = salaServicio.consultarTodasLasSalas();
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }

    @Test
    public void deberiaConsultarSalaPorNombre() throws LukaException {
        doReturn(salaMock)
            .doThrow(LukaException.class)
            .when(salaRepositorio).consultarSalaPorNombre(nullable(String.class));

        var resultado = salaServicio.consultarSalaPorNombre(salaMock.getNombre());
        assertNotNull(resultado);

        assertThrows(LukaException.class, () -> salaServicio.consultarSalaPorNombre(salaMock.getNombre()));
    }

    @Test
    public void deberiaEliminarSala() throws LukaException {
        doNothing().when(salaRepositorio).eliminarSala(nullable(String.class));
        doReturn(salaMock)
            .doThrow(LukaException.class)
            .when(salaRepositorio).consultarSalaPorNombre(nullable(String.class));

        var salaResultado = salaServicio.consultarSalaPorNombre(salaMock.getNombre());
        assertNotNull(salaResultado);

        salaServicio.eliminarSala(salaMock.getNombre());
        assertThrows(LukaException.class, () -> salaServicio.consultarSalaPorNombre(salaMock.getNombre()));
    }

    @Test
    public void deberiaAgregarUsuario() throws LukaException {
        var answer = new Answer<Sala>() {
            @Override
            public Sala answer(InvocationOnMock invocation) throws Throwable {
                var comprador = invocation.getArgument(1, String.class);
                salaMock.agregarComprador(comprador);
                return salaMock;
            }
        };

        doAnswer(answer)
            .doThrow(LukaException.class)
            .when(salaRepositorio).agregarUsuario(anyString(), anyString());

        assertTrue(salaMock.getCompradores().isEmpty());

        salaServicio.agregarUsuario(salaMock.getNombre(), "usuarioPrueba");
        assertFalse(salaMock.getCompradores().isEmpty());
        assertTrue(salaMock.getCompradores().contains("usuarioPrueba"));

    }

    @Test
    public void deberiaEliminarUsuario() throws LukaException {
        var answer = new Answer<Sala>() {
            @Override
            public Sala answer(InvocationOnMock invocation) throws Throwable {
                var comprador = invocation.getArgument(1, String.class);
                salaMock.getCompradores().remove(comprador);
                return salaMock;
            }
        };

        doAnswer(answer)
            .doThrow(LukaException.class)
            .when(salaRepositorio).eliminarUsuario(anyString(), nullable(String.class));

        doReturn(salaMock).when(salaRepositorio).consultarSalaPorNombre(anyString());

        salaMock.agregarComprador("usuarioPrueba");
        assertFalse(salaMock.getCompradores().isEmpty());

        salaServicio.eliminarUsuario(salaMock.getNombre(), "usuarioPrueba");

        var resultado = salaServicio.consultarSalaPorNombre(salaMock.getNombre());
        assertTrue(resultado.getCompradores().isEmpty());

        assertThrows(LukaException.class, () -> salaServicio.eliminarUsuario(salaMock.getNombre(), null));
        
    }

    @Test
    public void deberiaPujarPorProducto() throws LukaException {
        doReturn(true).when(salaRepositorio).existeComprador(anyString(), anyString());

        salaMock.getElementoSubasta().add(
            ElementoSubasta.builder()
                .producto(productoMock)
                .pujaMaxima(productoMock.getPrecio())
                .compradores(new PriorityQueue<>())
                .build()
        );

        doReturn(salaMock).when(salaRepositorio).consultarSalaPorNombre(anyString());

        var resultado = salaServicio.consultarSalaPorNombre(salaMock.getNombre());
        assertNotNull(resultado);
        assertFalse(resultado.getElementoSubasta().isEmpty());


    }
    
}
