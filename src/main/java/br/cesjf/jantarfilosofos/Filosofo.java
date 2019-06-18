package br.cesjf.jantarfilosofos;

import java.util.concurrent.Semaphore;

public class Filosofo extends Thread {

    // Declaração de variáveis e objetos
    private final int id;
    private final String nome;
    private Estado estado;
    private final Semaphore semaforo = new Semaphore(0);

    // Construtor
    Filosofo(int id, String nome) {
        this.id = id;
        this.nome = nome;
        estado = Estado.PENSANDO;
    }
    
    public void run() {
        try {
            while (true) {
                imprime();
                switch(estado) {
                    case PENSANDO:
                        // Executa o método que pausa a thread
                        pensando();
                        // Exclusão mútua - Começa a executar
                        JantarFilosofos.mutex.acquire();
                        // Altera o status para faminto
                        estado = Estado.FAMINTO; 
                        break;
                    case FAMINTO:
                        // Executa o método que verifica se os filósofos a direita e esquerda estão comendo
                        teste(this);
                        // Exclusão mútua - Termina a execução
                        JantarFilosofos.mutex.release();
                        // Bloqueia o semáforo
                        semaforo.acquire();
                        // Altera o estado para comendo
                        estado = Estado.COMENDO;
                        break;
                    case COMENDO:
                        comendo();
                        // Exclusão mútua - Começa a executar
                        JantarFilosofos.mutex.acquire();
                        // Altera o estado para pensando
                        estado = Estado.PENSANDO;
                        // Executa o método que verifica se os filósofos a direita e esquerda estão comendo
                        // Como são passados os vizinhos como parâmetro serve como uma cutucada para que os vizinhos comam
                        // Caso estejam famintos e seus vizinhos não estejam comendo
                        teste(esquerda());
                        teste(direita());
                        // Exclusão mútua - Termina a execução
                        JantarFilosofos.mutex.release();
                        break;          
                }
            }
        } catch(InterruptedException e) {}
    }
    
    // Método que pega o filósofo a esquerda
    public Filosofo esquerda() {
      return JantarFilosofos.filosofos.get(id == 0 ? JantarFilosofos.quantidadeFilosofos - 1 : id - 1);
    }

    // Método que pega o filósofo a direita
    public Filosofo direita() {
        return JantarFilosofos.filosofos.get((id + 1) % JantarFilosofos.quantidadeFilosofos);
    }

    // Método que verifica se o filósofo da esquerda ou direita está comendo, caso esteja libera o semáforo
    private static void teste(Filosofo f) {
        
        if (f.esquerda().estado != Estado.COMENDO
                && f.estado == Estado.FAMINTO
                && f.direita().estado != Estado.COMENDO) {
            f.estado = Estado.COMENDO;
            f.semaforo.release();
        }
        
    }

    // Definição do tempo que o filósofo fica pensando antes de ficar faminto
    private void pensando() {
        try {
            Thread.sleep((long) Math.round(Math.random() * 5000));
        } catch (InterruptedException e) {}
    }
    
    // Definição do tempo que o filósofo fica comendo antes de voltar a ficar pensando
    private void comendo() {
        try {
            Thread.sleep((long) Math.round(Math.random() * 5000));
        } catch (InterruptedException e) {}
    }

    // Método que faz a impressão
    private void imprime() {
        System.out.println("(" + (id + 1) + ") " + nome + " está " + estado.getDescricao());
    }
    
}