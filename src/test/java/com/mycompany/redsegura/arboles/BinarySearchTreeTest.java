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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BinarySearchTreeTest {

    private BinarySearchTree tree;

    private Vertex v(String label) {
        return new Vertex("X" + label, label, "Prueba");
    }

    @BeforeEach
    public void setUp() {
        tree = new BinarySearchTree();
        // Orden de insercion: raiz M, luego B, T, A, C
        for (String s : new String[]{"M", "B", "T", "A", "C"}) {
            tree.insert(v(s));
        }
    }

    @Test
    public void testInsertarYBuscar() {
        assertNotNull(tree.search("C"));
        assertEquals(5, tree.size());
    }

    @Test
    public void testBuscarInexistente() {
        assertNull(tree.search("Z"));
    }

    @Test
    public void testRecorridoInOrdenEstaOrdenado() {
        List<Vertex> r = tree.inOrder();
        assertEquals("A", r.get(0).getLabel());
        assertEquals("T", r.get(4).getLabel());
    }

    @Test
    public void testPreOrdenYPostOrden() {
        assertEquals("M", tree.preOrder().get(0).getLabel());
        assertEquals("M", tree.postOrder().get(4).getLabel());
    }

    @Test
    public void testAltura() {
        assertEquals(3, tree.height());
    }

    @Test
    public void testEliminarHoja() {
        assertTrue(tree.delete("A"));
        assertNull(tree.search("A"));
        assertEquals(4, tree.size());
    }

    @Test
    public void testEliminarNodoConDosHijos() {
        assertTrue(tree.delete("B"));
        assertNull(tree.search("B"));
        assertNotNull(tree.search("A"));
        assertNotNull(tree.search("C"));
    }

    @Test
    public void testEliminarInexistente() {
        assertFalse(tree.delete("Z"));
    }

    @Test
    public void testInsertarNuloLanzaError() {
        assertThrows(IllegalArgumentException.class, () -> tree.insert(null));
    }
}
