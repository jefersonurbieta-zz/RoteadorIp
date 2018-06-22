package br.com.urbieta.jeferson.model;

public class Package {

    private String sourceAddress;

    private String destinationAddress;

    private Integer routerPort;

    private String message;

    private Integer tll;

    public Package(String sourceAddress, String destinationAddress, Integer routerPort, String message) {
        this.sourceAddress = sourceAddress;
        this.destinationAddress = destinationAddress;
        this.routerPort = routerPort;
        this.message = message;
        this.tll = 5;
    }

    public Package(String data) {
        String[] parts = data.split("###");
        this.sourceAddress = parts[0];
        this.destinationAddress = parts[1];
        this.routerPort = Integer.valueOf(parts[2]);
        this.message = parts[3];
        this.tll = Integer.parseInt(parts[4]);
    }

    public void decreaseTLL() {
        this.tll--;
    }

    /*
     * Getters e Setters
     */

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public Integer getRouterPort() {
        return routerPort;
    }

    public void setRouterPort(Integer routerPort) {
        this.routerPort = routerPort;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTll() {
        return tll;
    }

    public void setTll(Integer tll) {
        this.tll = tll;
    }

    @Override
    public String toString() {
        return sourceAddress + "###" +
                destinationAddress + "###" +
                routerPort + "###" +
                message + "###" +
                tll.toString() + "###";
    }
}
