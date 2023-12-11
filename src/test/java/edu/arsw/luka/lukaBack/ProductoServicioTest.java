package edu.arsw.luka.lukaBack;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import edu.arsw.luka.lukaBack.domain.Categoria;
import edu.arsw.luka.lukaBack.domain.Producto;
import edu.arsw.luka.lukaBack.exception.LukaException;
import edu.arsw.luka.lukaBack.persistence.repositorio.ProductoRepositorio;
import edu.arsw.luka.lukaBack.services.ProductoServicioImpl;

@RunWith(MockitoJUnitRunner.class)
public class ProductoServicioTest {

    @Mock
    private ProductoRepositorio productoRepositorio;

    @InjectMocks
    private ProductoServicioImpl productoServicio;

    private Producto productoMock;
    private Producto productoMock2;

    public ProductoServicioTest() {
        init();
    }

    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setUp() {
        productoMock = new Producto();
        productoMock.setIdProducto("producto01");
        productoMock.setNombre("Producto de prueba");
        productoMock.setDescripcion("Descripcion de prueba");
        productoMock.setFoto("Foto de prueba");
        productoMock.setPrecio(100.0);
        productoMock.setCategoria(Categoria.COLECCIONABLES);
        productoMock.setVendedor("vendedor01");

        productoMock2 = new Producto(
            "producto02",
            "Producto de prueba 2",
            "Descripcion de prueba 2",
            "Foto de prueba 2",
            200.0,
            Categoria.COLECCIONABLES,
            "vendedor02"
        );
    }

    @Test
    public void deberiaAgregarProducto() throws LukaException {
        doReturn(productoMock).when(productoRepositorio).agregarProducto(productoMock);

        var productoResult = productoServicio.agregarProducto(productoMock);
        assertNotNull(productoResult);
        assertEquals(productoMock, productoResult);
    }

    @Test
    public void noDeberiaRetornarProductoSiHayError() throws LukaException {
        doThrow(NullPointerException.class)
            .doThrow(LukaException.class)
            .when(productoRepositorio).agregarProducto(nullable(Producto.class));

        assertThrows(NullPointerException.class, () -> productoServicio.agregarProducto(null));
        assertThrows(LukaException.class, () -> productoServicio.agregarProducto(productoMock));
    }

    @Test
    public void deberiaConsultarTodosLosProductos() {
        doReturn(List.of())
            .doReturn(List.of(productoMock))
            .when(productoRepositorio).consultarTodosLosProductos();

        var resultado = productoServicio.consultarTodosLosProductos();
        assertNotNull(resultado);
        assertTrue(resultado.size() >= 0);

        resultado = productoServicio.consultarTodosLosProductos();
        assertNotNull(resultado);
        assertTrue(resultado.size() >= 0);
        assertFalse(resultado.isEmpty());
        
    }


    @Test
    public void deberiaConsultarProductoPorId() throws LukaException  {
        doReturn(productoMock)
            .doReturn(productoMock2)
            .when(productoRepositorio).consultarProductoPorIdProducto(anyString());

        var resultado = productoServicio.consultarProductoPorIdProducto("producto01");
        assertNotNull(resultado);
        assertEquals(productoMock, resultado);

        resultado = productoServicio.consultarProductoPorIdProducto("producto02");
        assertNotNull(resultado);
        assertEquals(productoMock2, resultado);
        
    }

    @Test
    public void noDeberiaRetornarSiNoExisteProducto() throws LukaException {
        doThrow(LukaException.class).when(productoRepositorio).consultarProductoPorIdProducto(nullable(String.class));

        assertThrows(LukaException.class, () -> productoServicio.consultarProductoPorIdProducto("productoNoExistente"));
        assertThrows(LukaException.class, () -> productoServicio.consultarProductoPorIdProducto(null));
    }

    @Test
    public void deberiaConsultarProductoPorNombre() throws LukaException {
        doReturn(List.of(productoMock))
            .doReturn(List.of(productoMock2))
            .when(productoRepositorio).consultarProductoPorNombre(anyString());

        var resultado = productoServicio.consultarProductoPorNombre("Producto de prueba");
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(productoMock, resultado.get(0));

        resultado = productoServicio.consultarProductoPorNombre("Producto de prueba 2");
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(productoMock2, resultado.get(0));
    }

    @Test
    public void noDeberiaRetornarSiNoExisteProductoPorNombre() throws LukaException {
        doThrow(LukaException.class).when(productoRepositorio).consultarProductoPorNombre(nullable(String.class));

        assertThrows(LukaException.class, () -> productoServicio.consultarProductoPorNombre("productoNoExistente"));
        assertThrows(LukaException.class, () -> productoServicio.consultarProductoPorNombre(null));
    }

    @Test
    public void deberiaModificarProducto() throws LukaException {
        doReturn(productoMock2).when(productoRepositorio).modificarProducto(anyString(), any(Producto.class));

        var resultado = productoServicio.modificarProducto("producto01", productoMock2);
        assertNotNull(resultado);
        assertEquals(productoMock2, resultado);
    }

    @Test
    public void noDeberiaRetornarSiNoExisteProductoParaModificar() throws LukaException {
        doThrow(LukaException.class).when(productoRepositorio).modificarProducto(nullable(String.class), nullable(Producto.class));

        assertThrows(LukaException.class, () -> productoServicio.modificarProducto("productoNoExistente", productoMock2));
        assertThrows(LukaException.class, () -> productoServicio.modificarProducto(null, productoMock2));
        assertThrows(LukaException.class, () -> productoServicio.modificarProducto("producto01", null));
    }

    @Test
    public void deberiaEliminarProducto() throws LukaException {
        doNothing()
            .doThrow(LukaException.class)
            .when(productoRepositorio).eliminarProducto(nullable(String.class));

        productoServicio.eliminarProducto("producto01");
        assertThrows(LukaException.class, () -> productoServicio.eliminarProducto(null));
    }

    @Test
    public void deberiaConsultarProductosPorVendedor() throws LukaException {
        doReturn(List.of(productoMock))
            .doReturn(List.of(productoMock2))
            .when(productoRepositorio).consultarProductosPorVendedor(anyString());

        var resultado = productoServicio.consultarProductosPorVendedor("vendedor01");
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(productoMock, resultado.get(0));

        resultado = productoServicio.consultarProductosPorVendedor("vendedor02");
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(productoMock2, resultado.get(0));
    }

    @Test
    public void noDeberiaRetornarSiHayError() throws LukaException {
        doThrow(LukaException.class).when(productoRepositorio).consultarProductosPorVendedor(nullable(String.class));

        assertThrows(LukaException.class, () -> productoServicio.consultarProductosPorVendedor(null));
        assertThrows(LukaException.class, () -> productoServicio.consultarProductosPorVendedor("vendedorNoExistente"));

    }


    
}
