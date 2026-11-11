/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsegura.modelo;

/**
 *
 * @author acost
 */

public class Vertex {

    private String id;
    private String label;
    private String type;

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

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ID: " + id
                + " | Lugar: " + label
                + " | Tipo: " + type;
    }
}

