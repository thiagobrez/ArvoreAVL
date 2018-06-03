package com.company;

/**
 * @author João Grasel Cariolato
 * @author Thiago Brezinski
 *
 * Adaptado de: https://rosettacode.org/wiki/AVL_tree#Java
 */
public class NoAVL {

    private int chave;
    private int balanceamento;
    private int altura;
    private NoAVL esquerda;
    private NoAVL direita;
    private NoAVL pai;

    public NoAVL(int chave, NoAVL pai) {
        this.chave = chave;
        this.pai = pai;
    }

    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }

    public int getBalanceamento() {
        return balanceamento;
    }

    public void setBalanceamento(int balanceamento) {
        this.balanceamento = balanceamento;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public NoAVL getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(NoAVL esquerda) {
        this.esquerda = esquerda;
    }

    public NoAVL getDireita() {
        return direita;
    }

    public void setDireita(NoAVL direita) {
        this.direita = direita;
    }

    public NoAVL getPai() {
        return pai;
    }

    public void setPai(NoAVL pai) {
        this.pai = pai;
    }
}