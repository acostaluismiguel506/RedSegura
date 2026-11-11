/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsegura.modelo;

/**
 *
 * @author acost
 */
/**
 * Lugar de la red de RedSegura (universidad, centro de salud, nodo de
 * transporte, etc.).
 *
 * <p>Es inmutable: sus datos se fijan en el constructor y solo se consultan
 * con los metodos de lectura.</p>
 */
public class Vertex {

    private final String id;
    private final String label;
    private final String type;

    /**
     * Crea un vertice validando que ningun dato este vacio.
     *
     * @param id    identificador unico, por ejemplo "S01"
     * @param label nombre del lugar, por ejemplo "UAJS Sede C"
     * @param type  tipo de lugar, por ejemplo "Universidad"
     * @throws IllegalArgumentException si alguno es nulo o vacio
     */
    public Vertex(String id, String label, String type) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }

        if (label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("La etiqueta no puede estar vacía.");
        }

        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo no puede estar vacío.");
        }

        this.id = id;
        this.label = label;
        this.type = type;
    }

    /** @return identificador unico del vertice */
    public String getId() {
        return id;
    }

    /** @return nombre del lugar */
    public String getLabel() {
        return label;
    }

    /** @return tipo de lugar */
    public String getType() {
        return type;
    }

    /** @return descripcion legible del vertice */
    @Override
    public String toString() {
        return "ID: " + id
                + " | Lugar: " + label
                + " | Tipo: " + type;
    }
}