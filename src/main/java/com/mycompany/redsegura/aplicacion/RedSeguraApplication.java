/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsegura.aplicacion;

/**
 *
 * @author acost
 */


import java.util.Scanner;

/**
 * Aplicacion de consola de RedSegura. Solo habla con RedSeguraCore.
 */
public class RedSeguraApplication {

    public static void main(String[] args) {

        RedSeguraCore core = new RedSeguraCore();
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            String opcion = sc.nextLine().trim();
            System.out.println();

            switch (opcion) {
                case "1":
                    core.q1BuscarEnArboles("Hospital Universitario");
                    break;
                case "2":
                    core.q2Rotaciones();
                    break;
                case "3":
                    core.q3Representaciones();
                    break;
                case "4":
                    System.out.print("Vertice de origen (Enter = S02): ");
                    String raiz = sc.nextLine().trim();
                    if (raiz.isEmpty()) {
                        raiz = "S02";
                    }
                    try {
                        core.q4BfsDfs(raiz);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error controlado: " + e.getMessage());
                    }
                    break;
                case "5":
                    core.q5Dijkstra();
                    break;
                case "6":
                    core.q6BloquearR16();
                    break;
                case "7":
                    core.q7VerticeInexistente("S99");
                    break;
                case "8":
                    System.out.print("Origen (ej. S02): ");
                    String o = sc.nextLine().trim();
                    System.out.print("Destino (ej. S08): ");
                    String d = sc.nextLine().trim();
                    core.rutaPersonalizada(o, d);
                    break;
                case "9":
                    System.out.print("ID de la conexion a bloquear (ej. R16): ");
                    core.bloquearConexion(sc.nextLine().trim());
                    break;
                case "10":
                    core.restaurarConexiones();
                    break;
                case "0":
                    salir = true;
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n==============================");
        System.out.println("        REDSEGURA SINCELEJO");
        System.out.println("==============================");
        System.out.println("1. Q1 Buscar en ABB y AVL");
        System.out.println("2. Q2 Rotaciones AVL");
        System.out.println("3. Q3 Representaciones del grafo");
        System.out.println("4. Q4 BFS y DFS");
        System.out.println("5. Q5 Dijkstra S02 -> S08");
        System.out.println("6. Q6 Bloquear R16 y recalcular");
        System.out.println("7. Q7 Vertice inexistente");
        System.out.println("8. Ruta personalizada");
        System.out.println("9. Bloquear una conexion");
        System.out.println("10. Restaurar conexiones");
        System.out.println("0. Salir");
        System.out.print("Elige una opcion: ");
    }
}
