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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Busqueda en profundidad (DFS) sobre un {@link MixedGraph}.
 *
 * <p>Ofrece tres operaciones, todas con un conjunto de visitados:
 * recorrido desde una raiz, deteccion de ciclos y componentes debilmente
 * conexas. Complejidad de cada una: O(V + E) en tiempo y O(V) en espacio.</p>
 */
public class DepthFirstSearch {

    private final MixedGraph graph;

    /**
     * Crea el buscador para un grafo.
     *
     * @param graph grafo a recorrer; no puede ser nulo
     * @throws IllegalArgumentException si el grafo es nulo
     */
    public DepthFirstSearch(MixedGraph graph) {

        if (graph == null) {
            throw new IllegalArgumentException(
                    "El grafo no puede ser nulo.");
        }

        this.graph = graph;
    }

    // =====================================
    // RECORRIDO
    // =====================================

    /**
     * Recorre el grafo en profundidad respetando la orientacion de las
     * conexiones dirigidas.
     *
     * @param startId ID del vertice inicial
     * @return vertices en el orden en que fueron visitados
     * @throws IllegalArgumentException si el vertice no existe
     */
    public List<Vertex> search(String startId) {

        Vertex start = graph.getVertex(startId);

        if (start == null) {
            throw new IllegalArgumentException(
                    "No existe el vertice: " + startId);
        }

        List<Vertex> result = new ArrayList<>();

        Set<String> visited = new HashSet<>();

        dfsRecursive(start, visited, result);

        return result;
    }

    private void dfsRecursive(
            Vertex current,
            Set<String> visited,
            List<Vertex> result) {

        visited.add(current.getId());

        result.add(current);

        for (Edge edge :
                graph.getOutgoingEdges(current.getId())) {

            Vertex neighbor = otherEnd(edge, current);

            if (!visited.contains(neighbor.getId())) {

                dfsRecursive(neighbor, visited, result);
            }
        }
    }

    // =====================================
    // CICLOS
    // =====================================

    /**
     * Busca un ciclo en el grafo mixto.
     *
     * <p>Las conexiones dirigidas se recorren solo en su sentido; las no
     * dirigidas en ambos, pero sin volver por la misma conexion por la que se
     * llego (si no, toda conexion no dirigida seria un ciclo trivial).
     * Dos conexiones distintas entre los mismos vertices si forman ciclo.</p>
     *
     * @return lista de vertices del ciclo (el primero se repite al final),
     *         o una lista vacia si el grafo no tiene ciclos
     */
    public List<Vertex> findCycle() {

        Set<String> done = new HashSet<>();

        for (Vertex root : graph.getVertices()) {

            if (done.contains(root.getId())) {
                continue;
            }

            List<Vertex> path = new ArrayList<>();
            Set<String> onPath = new HashSet<>();

            List<Vertex> cycle =
                    cycleFrom(root, null, path, onPath, done);

            if (!cycle.isEmpty()) {
                return cycle;
            }
        }

        return new ArrayList<>();
    }

    /**
     * Indica si el grafo contiene al menos un ciclo.
     *
     * @return true si existe un ciclo
     */
    public boolean hasCycle() {
        return !findCycle().isEmpty();
    }

    private List<Vertex> cycleFrom(
            Vertex current,
            String arrivalEdgeId,
            List<Vertex> path,
            Set<String> onPath,
            Set<String> done) {

        path.add(current);
        onPath.add(current.getId());

        for (Edge edge :
                graph.getOutgoingEdges(current.getId())) {

            // No volver por la misma conexion por la que llegamos
            if (edge.getId().equals(arrivalEdgeId)) {
                continue;
            }

            Vertex neighbor = otherEnd(edge, current);

            if (onPath.contains(neighbor.getId())) {

                // Ciclo encontrado: desde neighbor hasta current
                List<Vertex> cycle = new ArrayList<>();
                int from = path.indexOf(neighbor);

                for (int i = from; i < path.size(); i++) {
                    cycle.add(path.get(i));
                }

                cycle.add(neighbor);
                return cycle;
            }

            if (!done.contains(neighbor.getId())) {

                List<Vertex> cycle = cycleFrom(
                        neighbor, edge.getId(), path, onPath, done);

                if (!cycle.isEmpty()) {
                    return cycle;
                }
            }
        }

        path.remove(path.size() - 1);
        onPath.remove(current.getId());
        done.add(current.getId());

        return new ArrayList<>();
    }

    // =====================================
    // COMPONENTES DEBILES
    // =====================================

    /**
     * Calcula las componentes debilmente conexas: grupos de vertices
     * conectados si se ignora la orientacion de las conexiones.
     *
     * @return lista de componentes; cada una es una lista de vertices
     */
    public List<List<Vertex>> weakComponents() {

        Set<String> visited = new HashSet<>();
        List<List<Vertex>> components = new ArrayList<>();

        for (Vertex root : graph.getVertices()) {

            if (visited.contains(root.getId())) {
                continue;
            }

            List<Vertex> component = new ArrayList<>();
            weakDfs(root, visited, component);
            components.add(component);
        }

        return components;
    }

    private void weakDfs(
            Vertex current,
            Set<String> visited,
            List<Vertex> component) {

        visited.add(current.getId());
        component.add(current);

        for (Vertex neighbor : undirectedNeighbors(current)) {

            if (!visited.contains(neighbor.getId())) {
                weakDfs(neighbor, visited, component);
            }
        }
    }

    /** Vecinos ignorando la orientacion (en ambos sentidos). */
    private Set<Vertex> undirectedNeighbors(Vertex vertex) {

        Set<Vertex> neighbors = new LinkedHashSet<>();

        for (Edge edge : graph.getEdges()) {

            if (edge.getOrigin().getId().equals(vertex.getId())) {
                neighbors.add(edge.getDestination());
            } else if (edge.getDestination().getId()
                    .equals(vertex.getId())) {
                neighbors.add(edge.getOrigin());
            }
        }

        return neighbors;
    }

    // =====================================
    // UTILIDADES
    // =====================================

    private Vertex otherEnd(Edge edge, Vertex current) {

        if (edge.getOrigin().getId().equals(current.getId())) {
            return edge.getDestination();
        }

        return edge.getOrigin();
    }

    /**
     * Imprime el recorrido DFS desde un vertice.
     *
     * @param startId ID del vertice inicial
     */
    public void display(String startId) {

        List<Vertex> result = search(startId);

        System.out.println("\n===== RECORRIDO DFS =====");

        for (Vertex vertex : result) {

            System.out.println(
                    vertex.getId() + " - " + vertex.getLabel());
        }

        System.out.println("Total visitados: " + result.size());
    }

    /**
     * Imprime el analisis completo: ciclo encontrado y componentes debiles.
     */
    public void displayAnalysis() {

        System.out.println("\n===== ANALISIS DFS: CICLOS Y COMPONENTES =====");

        List<Vertex> cycle = findCycle();

        if (cycle.isEmpty()) {
            System.out.println("Ciclos: el grafo no tiene ciclos.");
        } else {
            System.out.print("Ciclos: el grafo tiene al menos uno: ");
            for (int i = 0; i < cycle.size(); i++) {
                System.out.print(cycle.get(i).getId());
                if (i < cycle.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }

        List<List<Vertex>> components = weakComponents();

        System.out.println("Componentes debiles: " + components.size());

        for (int i = 0; i < components.size(); i++) {
            System.out.print("  Componente " + (i + 1) + ": ");
            for (Vertex v : components.get(i)) {
                System.out.print(v.getId() + " ");
            }
            System.out.println();
        }
    }
}