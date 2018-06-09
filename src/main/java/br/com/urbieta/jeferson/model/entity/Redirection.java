package br.com.urbieta.jeferson.model.entity;

public class Redirection {

    private String destino;

    private Integer mascara;

    private String gateway;

    private Integer interfaceSaida;

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Integer getMascara() {
        return mascara;
    }

    public void setMascara(Integer mascara) {
        this.mascara = mascara;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public Integer getInterfaceSaida() {
        return interfaceSaida;
    }

    public void setInterfaceSaida(Integer interfaceSaida) {
        this.interfaceSaida = interfaceSaida;
    }
}
