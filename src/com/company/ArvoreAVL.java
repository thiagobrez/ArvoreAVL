package com.company;

/**
 * @author João Grasel Cariolato
 * @author Thiago Brezinski
 *
 * Árvore AVL com alocação dinâmica.
 *
 * Adaptado de: https://rosettacode.org/wiki/AVL_tree#Java
 */
public class ArvoreAVL {

    private NoAVL raiz;

    /**
     * Recebe uma chave para inserção. Se a raiz estiver vazia, insere ali mesmo, senão, percorre os filhos
     * (menores para a esquerda, maiores para a direita) até encontrar um espaço vazio, onde é criado um novo
     * nó com o valor da chave. Se, durante o percorrimento, encontrar uma chave igual a que está sendo inserida,
     * retorna sem alterações.
     *
     * @param chave: chave do novo nó a ser inserido
     */
    public boolean inserir(int chave) {
        if (this.raiz == null) {
            this.raiz = new NoAVL(chave, null);
            return true;
        }

        NoAVL atual = this.raiz;
        while (true) {
            if (atual.getChave() == chave)
                return false;

            NoAVL pai = atual;

            boolean vaiParaEsquerda = atual.getChave() > chave;
            atual = vaiParaEsquerda ? atual.getEsquerda() : atual.getDireita();

            if (atual == null) {
                if (vaiParaEsquerda) {
                    pai.setEsquerda(new NoAVL(chave, pai));
                } else {
                    pai.setDireita(new NoAVL(chave, pai));
                }
                rebalancear(pai);
                break;
            }
        }
        return true;
    }

    /**
     * Recebe um nó para remoção.
     *
     * Se este nó não possuir filhos, verifica se possui pai. Se sim, apaga
     * a relação com seu pai e chama função para seu rebalanceamento. Se não, quer dizer que o nó é a raiz,
     * então limpa a raiz.
     *
     * Se o nó possuir filho na esquerda, e se esse filho possuir filhos na direita, percorre até o último
     * filho da direita deste, atribui sua chave ao nó inicialmente a ser removido e o remove. Se o nó possuir
     * filho na direita, faz-se o o mesmo procedimento, mas com os filhos esquerdos deste.
     *
     * @param no: nó a ser removido
     */
    private void removerRecursivo(NoAVL no) {
        if (no.getEsquerda() == null && no.getDireita() == null) {
            if (no.getPai() == null) {
                this.raiz = null;
            } else {
                NoAVL pai = no.getPai();
                if (pai.getEsquerda() == no) {
                    pai.setEsquerda(null);
                } else {
                    pai.setDireita(null);
                }
                rebalancear(pai);
            }
            return;
        }

        if (no.getEsquerda() != null) {
            NoAVL filho = no.getEsquerda();
            while (filho.getDireita() != null) filho = filho.getDireita();
            no.setChave(filho.getChave());
            removerRecursivo(filho);
        } else {
            NoAVL filho = no.getDireita();
            while (filho.getEsquerda() != null) filho = filho.getEsquerda();
            no.setChave(filho.getChave());
            removerRecursivo(filho);
        }
    }

    /**
     * Recebe uma chave para remoção. Se a raiz estiver vazia, retorna sem alterações, pois não existem nós.
     * Percorre os filhos até encontrar o que contenha a chave desejada, quando então chama a função de remoção
     * recursiva.
     *
     * @param chave: chave do nó a ser removido
     */
    public void remover(int chave) {
        if (this.raiz == null)
            return;

        NoAVL filho = this.raiz;
        while (filho != null) {
            NoAVL no = filho;
            filho = chave >= no.getChave() ? no.getDireita() : no.getEsquerda();
            if (chave == no.getChave()) {
                removerRecursivo(no);
                return;
            }
        }
    }

    /**
     * Recebe uma chave e faz uma busca binária para tentar encontrá-la nos nós. Se encontrado, retorna true,
     * senão, retorna false.
     *
     * @param chave: chave do nó sendo buscado
     */
    public boolean buscar(int chave) {
        if (this.raiz == null)
            return false;

        NoAVL filho = this.raiz;
        while (filho != null) {
            NoAVL no = filho;
            filho = chave >= no.getChave() ? no.getDireita() : no.getEsquerda();
            if (chave == no.getChave()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Recebe um nó chama função para recálculo de altura e balanceamento e, de acordo
     * com seu balanceamento, faz a rotação pertinente. Se o nó tiver um nó pai, rebalanceia
     * seu pai também.
     *
     * @param no: nó a ser rebalanceado
     */
    private void rebalancear(NoAVL no) {
        setBalanceamento(no);

        if (no.getBalanceamento() == -2) {
            if (altura(no.getEsquerda().getEsquerda()) >= altura(no.getEsquerda().getDireita()))
                no = rotacionaDireita(no);
            else
                no = rotacionaEsquerdaDepoisDireita(no);

        } else if (no.getBalanceamento() == 2) {
            if (altura(no.getDireita().getDireita()) >= altura(no.getDireita().getEsquerda()))
                no = rotacionaEsquerda(no);
            else
                no = rotacionaDireitaDepoisEsquerda(no);
        }

        if (no.getPai() != null) {
            rebalancear(no.getPai());
        } else {
            this.raiz = no;
        }
    }

    /**
     * Recebe um nó desbalanceado e faz uma rotação para a esquerda. O nó pivô (filho à direita
     * do desbalanceado) será o novo pai e o nó desbalanceado será o filho à esquerda.
     *
     * @param noDesbalanceado: nó a ser rotacionado
     */
    private NoAVL rotacionaEsquerda(NoAVL noDesbalanceado) {

        NoAVL direita = noDesbalanceado.getDireita();
        direita.setPai(noDesbalanceado.getPai());

        noDesbalanceado.setDireita(direita.getEsquerda());

        if (noDesbalanceado.getDireita() != null)
            noDesbalanceado.getDireita().setPai(noDesbalanceado);

        direita.setEsquerda(noDesbalanceado);
        noDesbalanceado.setPai(direita);

        if (direita.getPai() != null) {
            if (direita.getPai().getDireita() == noDesbalanceado) {
                direita.getPai().setDireita(direita);
            } else {
                direita.getPai().setEsquerda(direita);
            }
        }

        setBalanceamento(noDesbalanceado, direita);

        return direita;
    }

    /**
     * Recebe um nó desbalanceado e faz uma rotação para a direita. O nó pivô (filho à esquerda
     * do desbalanceado) será o novo pai e o nó desbalanceado será o filho à direita.
     * @param noDesbalanceado: nó a ser rotacionado
     */
    private NoAVL rotacionaDireita(NoAVL noDesbalanceado) {

        NoAVL esquerda = noDesbalanceado.getEsquerda();
        esquerda.setPai(noDesbalanceado.getPai());

        noDesbalanceado.setEsquerda(esquerda.getDireita());

        if (noDesbalanceado.getEsquerda() != null)
            noDesbalanceado.getEsquerda().setPai(noDesbalanceado);

        esquerda.setDireita(noDesbalanceado);
        noDesbalanceado.setPai(esquerda);

        if (esquerda.getPai() != null) {
            if (esquerda.getPai().getDireita() == noDesbalanceado) {
                esquerda.getPai().setDireita(esquerda);
            } else {
                esquerda.getPai().setEsquerda(esquerda);
            }
        }

        setBalanceamento(noDesbalanceado, esquerda);

        return esquerda;
    }

    /**
     * Recebe um nó, rotaciona seu filho esquerdo à esquerda e depois o rotaciona à direita.
     *
     * @param no: nó a ser rotacionado
     */
    private NoAVL rotacionaEsquerdaDepoisDireita(NoAVL no) {
        no.setEsquerda(rotacionaEsquerda(no.getEsquerda()));
        return rotacionaDireita(no);
    }

    /**
     * Recebe um nó, rotaciona seu filho direito à direita e depois o rotaciona à esquerda.
     *
     * @param no: nó a ser rotacionado
     */
    private NoAVL rotacionaDireitaDepoisEsquerda(NoAVL no) {
        no.setDireita(rotacionaDireita(no.getDireita()));
        return rotacionaEsquerda(no);
    }

    /**
     * Recebe um nó e retorna sua altura. Se o nó estiver vazio, retorna -1.
     *
     * @param no: nó a ser verificada a altura
     */
    private int altura(NoAVL no) {
        if (no == null) return -1;
        return no.getAltura();
    }

    /**
     * Recebe um ou mais nós. Para cada um, chama função para cálculo de sua altura e recalcula
     * seu balanceamento, através das alturas de seus filhos.
     *
     * @param nos: nós a serem rebalanceados
     */
    private void setBalanceamento(NoAVL... nos) {
        for (NoAVL no : nos) {
            calculaAltura(no);
            no.setBalanceamento(altura(no.getDireita()) - altura(no.getEsquerda()));
        }
    }

    /**
     * Caminha in-order pela árvore e imprime suas chaves e balanceamentos.
     */
    public void imprimeArvoreInOrder() {
        imprimeArvoreInOrderRecursivo(this.raiz);
    }

    /**
     * Percorre o filho da esquerda, em seguida a raiz e depois o filho da direita (caminhada in-order),
     * imprimindo sua chave e balanceamento, sucessivamente, até o final da árvore.
     *
     * @param no: nó atual sendo percorrido
     */
    private void imprimeArvoreInOrderRecursivo(NoAVL no) {
        if (no != null) {
            imprimeArvoreInOrderRecursivo(no.getEsquerda());
            System.out.println("Chave: " + no.getChave() + " | " + "Balanceamento: " + no.getBalanceamento());
            imprimeArvoreInOrderRecursivo(no.getDireita());
        }
    }

    /**
     * Recebe um nó. Verifica a altura de seus filhos à esquerda e direita e atribui a si a maior.
     *
     * @param no: nó a ser calculada a altura
     */
    private void calculaAltura(NoAVL no) {
        if (no != null) {
            no.setAltura(1 + Math.max(altura(no.getEsquerda()), altura(no.getDireita())));
        }
    }

}