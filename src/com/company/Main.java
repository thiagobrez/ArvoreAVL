package com.company;

/**
 * @author João Grasel Cariolato
 * @author Thiago Brezinski
 *
 * Adaptado de: https://rosettacode.org/wiki/AVL_tree#Java
 */
public class Main {

    public static void main(String[] args) {

        ArvoreAVL arvore = new ArvoreAVL();

        System.out.println("Inserindo valores de 1 a 10...");
        for (int i = 1; i <= 10; i++) arvore.inserir(i);

        System.out.println();
        System.out.print("Balanceamento (in-order): ");
        System.out.println();
        arvore.imprimeArvoreInOrder();


        System.out.println();
        System.out.println("Removendo valor 10...");

        arvore.remover(10);

        System.out.println();
        System.out.print("Balanceamento (in-order): ");
        System.out.println();
        arvore.imprimeArvoreInOrder();

        System.out.println();
        System.out.println("Removendo valor 2...");

        arvore.remover(2);

        System.out.println();
        System.out.print("Balanceamento (in-order): ");
        System.out.println();
        arvore.imprimeArvoreInOrder();

    }
}
