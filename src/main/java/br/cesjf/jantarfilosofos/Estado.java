package br.cesjf.jantarfilosofos;

public enum Estado {
    
    PENSANDO("pensando"),
    FAMINTO("faminto"),
    COMENDO("comendo");
    
    private String descricao;
 
    Estado(String descricao) {
        this.descricao = descricao;
    }
 
    public String getDescricao() {
        return descricao;
    }
}