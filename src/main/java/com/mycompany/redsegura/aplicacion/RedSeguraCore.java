/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsegura.aplicacion;

/**
 *
 * @author acost
 */

import com.mycompany.redsegura.algoritmos.BreadthFirstSearch;
import com.mycompany.redsegura.algoritmos.DepthFirstSearch;
import com.mycompany.redsegura.algoritmos.DijkstraSolver;
import com.mycompany.redsegura.arboles.AvlTree;
import com.mycompany.redsegura.arboles.BinarySearchTree;
import com.mycompany.redsegura.grafo.MixedGraph;
import com.mycompany.redsegura.modelo.Edge;
import com.mycompany.redsegura.modelo.Vertex;
import com.mycompany.redsegura.representaciones.GraphRepresentations;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase central de RedSegura. Guarda el grafo y los arboles
 * y ofrece metodos publicos para que la aplicacion los use.
 */
public class RedSeguraCore {

    private MixedGraph graph;
    private BinarySearchTree bst;
    private AvlTree avl;
        private Map<String, Edge> bloqueadas = new HashMap<>();

    public RedSeguraCore() {
        graph = RedSeguraFactory.createGraph();
        bst = new BinarySearchTree();
        avl = new AvlTree();

        // Se insertan los 12 vertices en orden S01..S12
        for (Vertex v : graph.getVertices()) {
            bst.insert(v);
            avl.insert(v);
        }
    }

    // ---------- Grafo ----------

    public void rutaMasCorta(String origen, String destino) {
        DijkstraSolver dijkstra = new DijkstraSolver(graph);
        dijkstra.displayShortestPath(origen, destino);
    }

    // ---------- Q1 ----------

    /**
     * Busca una etiqueta en el ABB y en el AVL y compara los pasos.
     */
    public void q1BuscarEnArboles(String etiqueta) {
        Vertex enAbb = bst.search(etiqueta);
        int pasosAbb = bst.getLastSearchSteps();

        Vertex enAvl = avl.search(etiqueta);
        int pasosAvl = avl.getLastSearchSteps();

        System.out.println("===== Q1: BUSQUEDA EN ABB vs AVL =====");
        System.out.println("Buscando: " + etiqueta);
        System.out.println("ABB -> " + (enAbb != null ? "encontrado" : "no encontrado")
                + " en " + pasosAbb + " pasos (altura " + bst.height() + ")");
        System.out.println("AVL -> " + (enAvl != null ? "encontrado" : "no encontrado")
                + " en " + pasosAvl + " pasos (altura " + avl.height() + ")");
    }

    // ---------- Q2 ----------

    /**
     * Muestra las cuatro rotaciones del AVL con tres claves cada una.
     */
    public void q2Rotaciones() {
        System.out.println("===== Q2: ROTACIONES AVL =====");
        mostrarRotacion("LL", "30", "20", "10");
        mostrarRotacion("RR", "10", "20", "30");
        mostrarRotacion("LR", "30", "10", "20");
        mostrarRotacion("RL", "10", "30", "20");
    }

    private void mostrarRotacion(String nombre, String a, String b, String c) {
        AvlTree arbol = new AvlTree();
        arbol.insert(new Vertex("T1", a, "Prueba"));
        arbol.insert(new Vertex("T2", b, "Prueba"));
        arbol.insert(new Vertex("T3", c, "Prueba"));

        System.out.println("\nRotacion " + nombre + ": se insertan "
                + a + ", " + b + ", " + c);
        System.out.print("Preorden despues de rotar: ");
        for (Vertex v : arbol.preOrder()) {
            System.out.print(v.getLabel() + " ");
        }
        System.out.println("\nAltura: " + arbol.height()
                + " | Balance raiz: " + arbol.getRootBalance());
    }

    // ---------- Q3 ----------

    /**
     * Imprime lista de adyacencia, matriz de adyacencia y matriz de incidencia.
     */
    public void q3Representaciones() {
        System.out.println("===== Q3: REPRESENTACIONES DEL GRAFO =====");
        GraphRepresentations rep = new GraphRepresentations(graph);
        rep.displayAll();
    }

    // ---------- Q4 ----------

    /**
     * Ejecuta BFS y DFS desde un vertice y explica por que difieren.
     */
    public void q4BfsDfs(String origen) {
        System.out.println("===== Q4: BFS Y DFS DESDE " + origen + " =====");
        new BreadthFirstSearch(graph).display(origen);
        new DepthFirstSearch(graph).display(origen);
        System.out.println("\nPor que el orden puede diferir:");
        System.out.println("- BFS usa una COLA: visita primero todos los vecinos "
                + "directos (por niveles) antes de ir mas lejos.");
        System.out.println("- DFS usa PILA/recursion: se va por una rama hasta "
                + "el fondo y luego retrocede.");
    }

    // ---------- Q5 ----------

    /**
     * Calcula la ruta minima de S02 a S08 (debe dar 24 minutos).
     */
    public void q5Dijkstra() {
        System.out.println("===== Q5: DIJKSTRA S02 -> S08 =====");
        rutaMasCorta("S02", "S08");
    }

    // ---------- Q6 ----------

    /**
     * Bloquea la conexion R16, recalcula la ruta y la restaura al final.
     */
    public void q6BloquearR16() {
        System.out.println("===== Q6: BLOQUEAR R16 Y RECALCULAR =====");
        System.out.println("--- Antes de bloquear ---");
        rutaMasCorta("S02", "S08");

        Edge r16 = graph.getEdge("R16");
        if (r16 == null) {
            System.out.println("La conexion R16 no existe.");
            return;
        }

        graph.removeEdge("R16");
        System.out.println("\n--- R16 bloqueada (S07 -> S08, 12 min) ---");
        rutaMasCorta("S02", "S08");

        graph.addEdge(r16); // se restaura para no danar otras consultas

        System.out.println("\nExplicacion: sin R16 la ruta por S07 ya no llega "
                + "directo al terminal. La nueva ruta S02-S01-S09-S10-S08 "
                + "cuesta 8+5+3+8 = 24, igual que la anterior 8+4+12 = 24. "
                + "Cambia el camino, pero el costo empata.");
    }

    // ---------- Q7 ----------

    /**
     * Consulta un vertice inexistente sin terminar el programa.
     */
    public void q7VerticeInexistente(String id) {
        System.out.println("===== Q7: VERTICE INEXISTENTE =====");
        try {
            rutaMasCorta(id, "S08");
        } catch (IllegalArgumentException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }
        System.out.println("El programa sigue funcionando.");
    }
    
    // ---------- Bloqueo manual de conexiones (para la demo) ----------

    /**
     * Bloquea una conexion por su ID (la quita del grafo temporalmente).
     */
    public void bloquearConexion(String id) {
        Edge e = graph.getEdge(id);
        if (e == null) {
            System.out.println("No existe la conexion " + id);
            return;
        }
        graph.removeEdge(id);
        bloqueadas.put(id, e);
        System.out.println("Conexion " + id + " bloqueada.");
    }

    /**
     * Restaura todas las conexiones bloqueadas con bloquearConexion.
     */
    public void restaurarConexiones() {
        for (Edge e : bloqueadas.values()) {
            graph.addEdge(e);
        }
        System.out.println("Conexiones restauradas: " + bloqueadas.size());
        bloqueadas.clear();
    }

    /**
     * Ruta minima con origen y destino elegidos, controlando errores.
     */
    public void rutaPersonalizada(String origen, String destino) {
        try {
            rutaMasCorta(origen, destino);
        } catch (IllegalArgumentException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }
    }
    
    // ---------- Analisis DFS ----------

    /**
     * Imprime ciclos y componentes debilmente conexas del grafo.
     */
    public void analisisDfs() {
        System.out.println("===== ANALISIS DFS =====");
        new DepthFirstSearch(graph).displayAnalysis();
    }
}