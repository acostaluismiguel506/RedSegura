/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsegura.aplicacion;

/**
 *
 * @author acost
 */

import com.mycompany.redsegura.modelo.Vertex;
import com.mycompany.redsegura.modelo.Edge;
import com.mycompany.redsegura.grafo.MixedGraph;

public class RedSeguraFactory {

    // Construye el grafo completo de RedSegura
    public static MixedGraph createGraph() {

        MixedGraph graph = new MixedGraph();

        // =====================================
        // 1. CREAR LOS 12 VERTICES
        // =====================================

        graph.addVertex(new Vertex(
                "S01", "UAJS Sede C", "Universidad"));

        graph.addVertex(new Vertex(
                "S02", "UAJS Sede E", "Universidad"));

        graph.addVertex(new Vertex(
                "S03", "Universidad de Sucre", "Universidad"));

        graph.addVertex(new Vertex(
                "S04", "CECAR", "Universidad"));

        graph.addVertex(new Vertex(
                "S05", "Hospital Universitario", "Salud"));

        graph.addVertex(new Vertex(
                "S06", "Clinica Las Americas", "Salud"));

        graph.addVertex(new Vertex(
                "S07", "Estacion Central SETP", "Transporte"));

        graph.addVertex(new Vertex(
                "S08", "Terminal de Transporte", "Transporte"));

        graph.addVertex(new Vertex(
                "S09", "Parque Santander", "Punto de encuentro"));

        graph.addVertex(new Vertex(
                "S10", "Mercado de Sincelejo", "Comercio"));

        graph.addVertex(new Vertex(
                "S11", "PEP Argelia", "Paradero"));

        graph.addVertex(new Vertex(
                "S12", "Glorieta Sincelejito", "Movilidad"));


        // =====================================
        // 2. CREAR LAS 18 CONEXIONES
        // =====================================

        // R01: S01 -> S07
        addConnection(graph, "R01",
                "Corredor universitario",
                "S01", "S07", 4, true);

        // R02: S07 -> S01
        addConnection(graph, "R02",
                "Retorno por via alterna",
                "S07", "S01", 6, true);

        // R03: S01 <-> S09
        addConnection(graph, "R03",
                "Corredor peatonal",
                "S01", "S09", 5, false);

        // R04: S02 <-> S01
        addConnection(graph, "R04",
                "Conexion entre sedes",
                "S02", "S01", 8, false);

        // R05: S02 -> S06
        addConnection(graph, "R05",
                "Ruta de atencion",
                "S02", "S06", 10, true);

        // R06: S06 -> S05
        addConnection(graph, "R06",
                "Corredor clinico",
                "S06", "S05", 4, true);

        // R07: S05 <-> S09
        addConnection(graph, "R07",
                "Via hospitalaria",
                "S05", "S09", 6, false);

        // R08: S09 <-> S10
        addConnection(graph, "R08",
                "Centro historico",
                "S09", "S10", 3, false);

        // R09: S10 -> S08
        addConnection(graph, "R09",
                "Salida al terminal",
                "S10", "S08", 8, true);

        // R10: S08 -> S10
        addConnection(graph, "R10",
                "Ingreso desde terminal",
                "S08", "S10", 9, true);

        // R11: S03 <-> S12
        addConnection(graph, "R11",
                "Corredor Puerta Roja",
                "S03", "S12", 4, false);

        // R12: S12 <-> S09
        addConnection(graph, "R12",
                "Troncal al centro",
                "S12", "S09", 7, false);

        // R13: S04 <-> S11
        addConnection(graph, "R13",
                "Conexion universitaria",
                "S04", "S11", 6, false);

        // R14: S11 -> S07
        addConnection(graph, "R14",
                "Ruta SETP al centro",
                "S11", "S07", 8, true);

        // R15: S07 <-> S09
        addConnection(graph, "R15",
                "Transferencia central",
                "S07", "S09", 3, false);

        // R16: S07 -> S08
        addConnection(graph, "R16",
                "Ruta SETP al terminal",
                "S07", "S08", 12, true);

        // R17: S09 -> S03
        addConnection(graph, "R17",
                "Ruta hacia universidad",
                "S09", "S03", 7, true);

        // R18: S12 -> S04
        addConnection(graph, "R18",
                "Ruta hacia CECAR",
                "S12", "S04", 9, true);


        return graph;
    }


    // Metodo auxiliar para registrar conexiones
    private static void addConnection(
            MixedGraph graph,
            String id,
            String label,
            String originId,
            String destinationId,
            double weight,
            boolean directed) {

        Vertex origin = graph.getVertex(originId);
        Vertex destination = graph.getVertex(destinationId);

        if (origin == null || destination == null) {
            throw new IllegalArgumentException(
                    "No se pueden crear las conexiones "
                    + id + ": vertices inexistentes.");
        }

        Edge edge = new Edge(
                id,
                label,
                origin,
                destination,
                weight,
                directed
        );

        graph.addEdge(edge);
    }
}
