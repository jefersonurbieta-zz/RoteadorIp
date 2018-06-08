package br.com.urbieta.jeferson.model.entity;

public class Package {

    private String roteador;

    private String origem;

    private String destino;

    private Integer porta;

    private String mensagem;

    public Package(String roteador, String origem, String destino, Integer porta, String mensagem) {
        this.roteador = roteador;
        this.origem = origem;
        this.destino = destino;
        this.porta = porta;
        this.mensagem = mensagem;
    }

    public String getRoteador() {
        return roteador;
    }

    public void setRoteador(String roteador) {
        this.roteador = roteador;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Integer getPorta() {
        return porta;
    }

    public void setPorta(Integer porta) {
        this.porta = porta;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
