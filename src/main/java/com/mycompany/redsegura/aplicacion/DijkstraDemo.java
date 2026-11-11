/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsegura.aplicacion;

/**
 *
 * @author acost
 */


public class DijkstraDemo {

    public static void main(String[] args) {

        RedSeguraCore core = new RedSeguraCore();

        core.q3Representaciones();
        core.q4BfsDfs("S02");
        core.q5Dijkstra();
        core.q6BloquearR16();
        core.q7VerticeInexistente("S99");
    }
}