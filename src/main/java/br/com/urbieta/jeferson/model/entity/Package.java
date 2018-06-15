package br.com.urbieta.jeferson.model.entity;

public class Package {

    private String roteador;

    private String origem;

    private String destino;

    private Integer porta;

    private String mensagem;

    private Integer tll;

    public Package(String roteador, String origem, String destino, Integer porta, String mensagem) {
        this.roteador = roteador;
        this.origem = origem;
        this.destino = destino;
        this.porta = porta;
        this.mensagem = mensagem;
        this.tll = 5;
    }

    public Package(String data) {
        String[] parts = data.split("###");
        this.origem = parts[0];
        this.destino = parts[1];
        this.porta = Integer.valueOf(parts[2]);
        this.mensagem = parts[3];
        this.tll = Integer.valueOf(parts[4]);
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

    @Override
    public String toString() {
        return origem + "###" +
                destino + "###" +
                porta + "###" +
                mensagem + "###" +
                tll;
    }
}
