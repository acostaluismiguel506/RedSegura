/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsegura.algoritmos;

/**
 *
 * @author acost
 */

import com.mycompany.redsegura.grafo.MixedGraph;
import com.mycompany.redsegura.modelo.Edge;
import com.mycompany.redsegura.modelo.Vertex;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Busqueda en anchura (BFS) sobre un {@link MixedGraph}.
 *
 * <p>Usa una cola y un conjunto de visitados. Ademas del orden de visita
 * calcula el arbol BFS: el padre y el nivel de cada vertice alcanzado.
 * Respeta la orientacion de las conexiones dirigidas.
 * Complejidad: O(V + E) en tiempo y O(V) en espacio.</p>
 */
public class BreadthFirstSearch {

    private final MixedGraph graph;

    /**
     * Crea el buscador para un grafo.
     *
     * @param graph grafo a recorrer; no puede ser nulo
     * @throws IllegalArgumentException si el grafo es nulo
     */
    public BreadthFirstSearch(MixedGraph graph) {

        if (graph == null) {
            throw new IllegalArgumentException(
                    "El grafo no puede ser nulo.");
        }

        this.graph = graph;
    }

    /**
     * Recorre el grafo por niveles desde un vertice.
     *
     * @param startId ID del vertice inicial
     * @return vertices en el orden en que fueron visitados
     * @throws IllegalArgumentException si el vertice no existe
     */
    public List<Vertex> search(String startId) {

        List<Vertex> order = new ArrayList<>();
        run(startId, order, new LinkedHashMap<>(), new LinkedHashMap<>());
        return order;
    }

    /**
     * Devuelve el padre de cada vertice alcanzado en el arbol BFS.
     * La raiz tiene padre nulo.
     *
     * @param startId ID del vertice raiz
     * @return mapa ID del vertice a ID de su padre, en orden de visita
     * @throws IllegalArgumentException si el vertice no existe
     */
    public Map<String, String> getParents(String startId) {

        Map<String, String> parents = new LinkedHashMap<>();
        run(startId, new ArrayList<>(), parents, new LinkedHashMap<>());
        return parents;
    }

    /**
     * Devuelve el nivel (distancia en aristas) de cada vertice alcanzado.
     * La raiz esta en el nivel 0.
     *
     * @param startId ID del vertice raiz
     * @return mapa ID del vertice a su nivel, en orden de visita
     * @throws IllegalArgumentException si el vertice no existe
     */
    public Map<String, Integer> getLevels(String startId) {

        Map<String, Integer> levels = new LinkedHashMap<>();
        run(startId, new ArrayList<>(), new LinkedHashMap<>(), levels);
        return levels;
    }

    /** Ejecuta el BFS y llena orden, padres y niveles. */
    private void run(
            String startId,
            List<Vertex> order,
            Map<String, String> parents,
            Map<String, Integer> levels) {

        Vertex start = graph.getVertex(startId);

        if (start == null) {
            throw new IllegalArgumentException(
                    "No existe el vertice: " + startId);
        }

        // Cola para administrar los vertices pendientes
        Queue<Vertex> queue = new ArrayDeque<>();

        // Evita visitar un vertice varias veces
        Set<String> visited = new HashSet<>();

        queue.add(start);
        visited.add(start.getId());
        parents.put(start.getId(), null);
        levels.put(start.getId(), 0);

        while (!queue.isEmpty()) {

            Vertex current = queue.poll();

            order.add(current);

            for (Edge edge :
                    graph.getOutgoingEdges(current.getId())) {

                Vertex neighbor;

                if (edge.getOrigin().getId()
                        .equals(current.getId())) {

                    neighbor = edge.getDestination();

                } else {

                    neighbor = edge.getOrigin();
                }

                if (!visited.contains(neighbor.getId())) {

                    visited.add(neighbor.getId());
                    parents.put(neighbor.getId(), current.getId());
                    levels.put(neighbor.getId(),
                            levels.get(current.getId()) + 1);

                    queue.add(neighbor);
                }
            }
        }
    }

    /**
     * Imprime el recorrido BFS desde un vertice.
     *
     * @param startId ID del vertice inicial
     */
    public void display(String startId) {

        List<Vertex> result = search(startId);

        System.out.println("\n===== RECORRIDO BFS =====");

        for (Vertex vertex : result) {
            System.out.println(
                    vertex.getId() + " - " + vertex.getLabel());
        }

        System.out.println("Total visitados: " + result.size());
    }

    /**
     * Imprime el arbol BFS: cada vertice con su nivel y su padre.
     *
     * @param startId ID del vertice raiz
     */
    public void displayTree(String startId) {

        Map<String, String> parents = getParents(startId);
        Map<String, Integer> levels = getLevels(startId);

        System.out.println("\n===== ARBOL BFS DESDE " + startId + " =====");

        for (String id : parents.keySet()) {

            String parent = parents.get(id);
            int level = levels.get(id);

            StringBuilder indent = new StringBuilder();
            for (int i = 0; i < level; i++) {
                indent.append("   ");
            }

            System.out.println(indent + id + " ("
                    + graph.getVertex(id).getLabel() + ")"
                    + "  nivel " + level
                    + (parent == null ? "  [raiz]" : "  padre: " + parent));
        }

        System.out.println("Vertices en el arbol: " + parents.size());
    }
}
