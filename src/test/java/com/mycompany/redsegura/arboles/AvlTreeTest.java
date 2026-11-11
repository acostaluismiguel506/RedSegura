/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsegura.arboles;

/**
 *
 * @author acost
 */


import com.mycompany.redsegura.modelo.Vertex;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AvlTreeTest {

    private AvlTree crear(String a, String b, String c) {
        AvlTree t = new AvlTree();
        t.insert(new Vertex("T1", a, "Prueba"));
        t.insert(new Vertex("T2", b, "Prueba"));
        t.insert(new Vertex("T3", c, "Prueba"));
        return t;
    }

    private void verificarRaiz20(AvlTree t) {
        List<Vertex> pre = t.preOrder();
        assertEquals("20", pre.get(0).getLabel());
        assertEquals("10", pre.get(1).getLabel());
        assertEquals("30", pre.get(2).getLabel());
        assertEquals(2, t.height());
        assertEquals(0, t.getRootBalance());
    }

    @Test
    public void testRotacionLL() {
        verificarRaiz20(crear("30", "20", "10"));
    }

    @Test
    public void testRotacionRR() {
        verificarRaiz20(crear("10", "20", "30"));
    }

    @Test
    public void testRotacionLR() {
        verificarRaiz20(crear("30", "10", "20"));
    }

    @Test
    public void testRotacionRL() {
        verificarRaiz20(crear("10", "30", "20"));
    }

    @Test
    public void testBalanceConMuchosDatosOrdenados() {
        AvlTree t = new AvlTree();
        for (int i = 100; i < 164; i++) {
            t.insert(new Vertex("V" + i, String.valueOf(i), "Prueba"));
        }
        assertEquals(64, t.size());
        // Un AVL de 64 nodos nunca pasa de altura ~8; un ABB degenerado daria 64
        assertTrue(t.height() <= 8);
        assertTrue(Math.abs(t.getRootBalance()) <= 1);
    }

    @Test
    public void testEliminarMantieneBalance() {
        AvlTree t = crear("20", "10", "30");
        t.insert(new Vertex("T4", "5", "Prueba"));
        assertTrue(t.delete("30"));
        assertNull(t.search("30"));
        assertTrue(Math.abs(t.getRootBalance()) <= 1);
    }

    @Test
    public void testEliminarInexistente() {
        assertFalse(crear("20", "10", "30").delete("99"));
    }

    @Test
    public void testBuscarCuentaPasos() {
        AvlTree t = crear("10", "20", "30");
        assertNotNull(t.search("20"));
        assertEquals(1, t.getLastSearchSteps());
    }
}
