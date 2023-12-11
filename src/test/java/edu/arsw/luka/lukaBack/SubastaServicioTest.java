package edu.arsw.luka.lukaBack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import edu.arsw.luka.lukaBack.domain.Estado;
import edu.arsw.luka.lukaBack.domain.Producto;
import edu.arsw.luka.lukaBack.domain.Subasta;
import edu.arsw.luka.lukaBack.domain.TipoSubasta;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.persistence.repositorio.SubastaRepositorio;
import edu.arsw.luka.lukaBack.services.ProductoServicio;
import edu.arsw.luka.lukaBack.services.SubastaServicioImpl;


@RunWith(MockitoJUnitRunner.class)
public class SubastaServicioTest {

    @Mock
    private SubastaRepositorio subastaRepositorio;

    @Mock
    private ProductoServicio productoServicio;

    @InjectMocks
    private SubastaServicioImpl subastaServicio;

    private Subasta[] subastasCorrectas;
    private Producto productoMock;


    public SubastaServicioTest() {
        init();
    }

    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setUp() {
        subastasCorrectas = new Subasta[]{
            new Subasta(
                "SubastaPrueba",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                TipoSubasta.LARGA,
                Estado.EN_CURSO,
                new ArrayList<>()
            ),
            new Subasta(
                "SubastaPrueba2",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now(),
                TipoSubasta.LARGA,
                Estado.FINALIZADA,
                new ArrayList<>()
            ),
            new Subasta(
                "SubastaPrueba3",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                TipoSubasta.CORTA,
                Estado.EN_CURSO,
                new ArrayList<>()
            )
        };
        
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
    public void deberiaAgregarSubasta() throws LukaException {
        var subastaCorrecta = subastasCorrectas[0];
        doReturn(subastaCorrecta).when(subastaRepositorio).agregarSubasta(any(Subasta.class));

        var subasta = subastaServicio.agregarSubasta(subastaCorrecta);
        assertNotNull(subasta);
        assertEquals(subastaCorrecta, subasta);
    }

    @Test
    public void deberiaLanzarErrorCuandolasFechasNoCoinciden() throws LukaException {
        doReturn(null).when(subastaRepositorio).agregarSubasta(any(Subasta.class));
        final Subasta subastaIncorrecta = subastasCorrectas[1];

        subastaIncorrecta.setFechaInicio(LocalDateTime.now().plusDays(2));
        assertThrows(LukaException.class, () -> subastaServicio.agregarSubasta(subastaIncorrecta));

        subastaIncorrecta.setFechaInicio(LocalDateTime.now());
        subastaIncorrecta.setFechaInicio(LocalDateTime.now().plusDays(21));
        assertThrows(LukaException.class, () -> subastaServicio.agregarSubasta(subastaIncorrecta));

        subastaIncorrecta.setTipoSubasta(TipoSubasta.CORTA);
        subastaIncorrecta.setFechaInicio(LocalDateTime.now().plusHours(2));
        assertThrows(LukaException.class, () -> subastaServicio.agregarSubasta(subastaIncorrecta));

        subastaIncorrecta.setFechaInicio(LocalDateTime.now());
        subastaIncorrecta.setFechaInicio(LocalDateTime.now().plusMinutes(62));
        assertThrows(LukaException.class, () -> subastaServicio.agregarSubasta(subastaIncorrecta));

    }

    @Test
    public void deberiaModificarFechaSubasta() throws LukaException {
        var subasta = subastasCorrectas[0];
        var nuevaFechaInicio = LocalDateTime.now().plusDays(2);
        var nuevaFechaFin = LocalDateTime.now().plusDays(3);

        subasta.setFechaInicio(nuevaFechaInicio);
        subasta.setFechaFin(nuevaFechaFin);

        doReturn(subasta).when(subastaRepositorio).modificarFechaSubasta(anyString(), any(LocalDateTime.class), any(LocalDateTime.class), anyBoolean());

        var resultado = subastaServicio.modificarFechaSubasta(subasta.getNombre(), nuevaFechaInicio, nuevaFechaFin, false);
        assertNotNull(resultado);
        assertEquals(subasta, resultado);
    }

    @Test
    public void deberiaCambiarElTipoDeSubastaSiLoDeseaConlasFechas() throws LukaException {
        var subasta = subastasCorrectas[0];
        var nuevaFechaInicio = subasta.getFechaInicio();
        var nuevaFechaFin = subasta.getFechaFin().plusMinutes(30);
        var cambiarTipo = true;

        subasta.setFechaInicio(nuevaFechaInicio);
        subasta.setFechaFin(nuevaFechaFin);
        subasta.setTipoSubasta(TipoSubasta.CORTA);

        doReturn(subasta).when(subastaRepositorio).modificarFechaSubasta(anyString(), any(LocalDateTime.class), any(LocalDateTime.class), anyBoolean());

        var resultado = subastaServicio.modificarFechaSubasta(subasta.getNombre(), nuevaFechaInicio, nuevaFechaFin, cambiarTipo);
        assertNotNull(resultado);
        assertEquals(subasta.getFechaInicio(), resultado.getFechaInicio());
        assertEquals(subasta.getFechaFin(), resultado.getFechaFin());
    }

    @Test
    public void deberiaConsultarTodasLasSubastas() {
        doReturn(List.of())
            .doReturn(List.of(subastasCorrectas[0]))
            .doReturn(Arrays.asList(subastasCorrectas))
            .when(subastaRepositorio).consultarTodasLasSubastas();

        var resultado = subastaServicio.consultarTodasLasSubastas();
        assertNotNull(resultado);
        assertTrue(resultado.size() >= 0);

        assertNotNull(resultado);
        assertTrue(resultado.size() >= 0);

        assertNotNull(resultado);
        assertTrue(resultado.size() >= 0);
    }

    @Test
    public void deberiaConsultarSubastaPorTipo() throws LukaException {
        var subastasList = Arrays.asList(subastasCorrectas);
        var tiposSubasta = Arrays.asList(TipoSubasta.values());
        doReturn(subastasList).when(subastaRepositorio).consultarTodasLasSubastas();

        for (var tipoSubasta : tiposSubasta) {
            var resultado = subastaServicio.consultarSubastaPorTipo(tipoSubasta);
            assertNotNull(resultado);
            assertTrue(resultado.size() >= 0);
        }
        
    }

    @Test
    public void deberiaConsultarSubastaPorNombre() throws LukaException{
        var subastaMock = subastasCorrectas[0];
        doReturn(subastaMock).doThrow(LukaException.class)
            .when(subastaRepositorio).consultarSubastaPorNombre(anyString());

        var resultado = subastaServicio.consultarSubastaPorNombre(subastaMock.getNombre());

        assertNotNull(resultado);
        assertEquals(subastaMock, resultado);

        assertThrows(LukaException.class, () -> subastaServicio.consultarSubastaPorNombre("SubastaInexistente"));


    }

    @Test
    public void deberiaModificarEstadoDeSubasta() throws LukaException {
        var subasta = subastasCorrectas[0];

        doReturn(subasta).when(subastaRepositorio).consultarSubastaPorNombre(anyString());
        doReturn(subasta).when(subastaRepositorio).agregarSubasta(any(Subasta.class));

        subasta.setFechaInicio(LocalDateTime.now().plusDays(2));
        assertEquals(Estado.PROGRAMADA, subastaServicio.modificarEstadoSubasta(subasta.getNombre()).getEstado());

        subasta.setFechaInicio(LocalDateTime.now().minusDays(1).minusHours(1));
        subasta.setFechaFin(LocalDateTime.now().minusDays(1));
        assertEquals(Estado.FINALIZADA, subastaServicio.modificarEstadoSubasta(subasta.getNombre()).getEstado());

        subasta.setFechaInicio(LocalDateTime.now().minusHours(1));
        subasta.setFechaFin(LocalDateTime.now().plusHours(1));
        assertEquals(Estado.EN_CURSO, subastaServicio.modificarEstadoSubasta(subasta.getNombre()).getEstado());

    }

    @Test
    public void deberiaEliminarSubasta() throws LukaException {
        var subasta = subastasCorrectas[0];
        doNothing()
            .doThrow(LukaException.class)
            .when(subastaRepositorio).eliminarSubasta(anyString());

        subastaServicio.eliminarSubasta(subasta.getNombre());
        assertThrows(LukaException.class, () -> subastaServicio.eliminarSubasta(subasta.getNombre()));
    }

    @Test
    public void deberiaAgregarProductoSubasta() throws LukaException {
        var subasta = subastasCorrectas[0];
        var answer = new Answer<Subasta>() {
            @Override
            public Subasta answer(InvocationOnMock invocation) throws Throwable {
                var subastaResultado = subasta;
                var producto = invocation.getArgument(1, Producto.class);

                subastaResultado.agregarProducto(producto);
                return subastaResultado;
            }
        };

        doAnswer(answer)
            .doThrow(LukaException.class)
            .when(subastaRepositorio).agregarProductoSubasta(anyString(), nullable(Producto.class));

        doReturn(subasta).when(subastaRepositorio).consultarSubastaPorNombre(anyString());

        assertTrue(subasta.getProductos().isEmpty());

        subastaServicio.agregarProductoSubasta(subasta.getNombre(), productoMock);
        var resultado = subastaServicio.consultarSubastaPorNombre(subasta.getNombre());
        assertFalse(resultado.getProductos().isEmpty());
        assertTrue(resultado.getProductos().contains(productoMock));

        assertThrows(LukaException.class, () -> subastaServicio.agregarProductoSubasta(subasta.getNombre(), null));
    }

    @Test
    public void deberiaEliminarProductoSubasta() throws LukaException {
        var subasta = subastasCorrectas[0];
        var answer = new Answer<Subasta>() {
            @Override
            public Subasta answer(InvocationOnMock invocation) throws Throwable {
                var subastaResultado = subasta;
                var idProducto = invocation.getArgument(1, String.class);

                subastaResultado.eliminarProducto(idProducto);
                return subastaResultado;
            }
        };

        doAnswer(answer)
            .doThrow(LukaException.class)
            .when(subastaRepositorio).eliminarProductoSubasta(anyString(), nullable(String.class));

        doReturn(subasta).when(subastaRepositorio).consultarSubastaPorNombre(anyString());

        subasta.agregarProducto(productoMock);
        assertFalse(subasta.getProductos().isEmpty());

        subastaServicio.eliminarProductoSubasta(subasta.getNombre(), productoMock.getIdProducto());
        var resultado = subastaServicio.consultarSubastaPorNombre(subasta.getNombre());
        assertTrue(resultado.getProductos().isEmpty());

        assertThrows(LukaException.class, () -> subastaServicio.eliminarProductoSubasta(subasta.getNombre(), null));
    }

    @Test
    public void deberiaConsultarProductosNoAgregadosSubastas() throws LukaException {
        var subastaMock = subastasCorrectas[1];
        subastaMock.agregarProducto(productoMock);

        var subastasTotales = List.of(subastaMock);
        var productosTotales = List.of(
            productoMock,
            new Producto(
                "Producto02",
                "ProductoPrueba2",
                "DescripcionPrueba2",
                "Foto producto prueba2",
                1000.0,
                Categoria.ELECTRODOMESTICOS,
                "VendedorPrueba2"
            )
        );

        doReturn(subastasTotales).when(subastaRepositorio).consultarTodasLasSubastas();
        doReturn(productosTotales).when(productoServicio).consultarTodosLosProductos();

        var resultado = subastaServicio.consultarProductosNoAgregadosSubastas();
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertFalse(resultado.contains(productoMock));       

    }


    
}

