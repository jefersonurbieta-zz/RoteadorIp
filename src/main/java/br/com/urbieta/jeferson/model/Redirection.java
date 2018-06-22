package br.com.urbieta.jeferson.model;

public class Redirection {

    private String destiny;

    private Integer mask;

    private String gateway;

    private Integer interfaceOutput;

    public boolean isDirect() {
        return gateway != null && gateway.equals("0.0.0.0");
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public Integer getMask() {
        return mask;
    }

    public void setMask(Integer mask) {
        this.mask = mask;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public Integer getInterfaceOutput() {
        return interfaceOutput;
    }

    public void setInterfaceOutput(Integer interfaceOutput) {
        this.interfaceOutput = interfaceOutput;
    }
}
