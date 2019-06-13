package br.cesjf.jantarfilosofos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class JantarFilosofos {

    // Declaração das variáveis, listas e objetos
    private static final int quantidadeFilosofos = 5;
    private static final Semaphore mutex = new Semaphore(1);
    private static final List<String> nomes = new ArrayList<String>();
    private static final List<Filosofo> filosofos = new ArrayList<Filosofo>();
    
    public static void main(String[] args) {
        
        // Inclusão de nomes de filósofos na lista de nomes
        nomes.add("Sócrates");
        nomes.add("Platão");
        nomes.add("Aristóteles");
        nomes.add("Pitágoras");
        nomes.add("Galileu Galilei");
        
        // Instanciação dos filósofos
        for (int i = 0; i < quantidadeFilosofos; i++) {
            filosofos.add(new Filosofo(i, nomes.get(i)));
        }
        
        // Inicialização das threads
        for (Thread t : filosofos) {
            t.start();
        }
        
    }
    
    // Getters das variáveis, listas e objetos
    public static int getQuantidadeFilosofos() {
        return quantidadeFilosofos;
    }
    
    public static List<Filosofo> getFilosofos() {
        return filosofos;
    }
    
    public static Semaphore getMutex() {
        return mutex;
    }
    
}