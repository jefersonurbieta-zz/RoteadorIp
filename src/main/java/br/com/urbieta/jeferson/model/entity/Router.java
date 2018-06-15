package br.com.urbieta.jeferson.model.entity;

import br.com.urbieta.jeferson.commom.ReceivePackegeThread;

public class Router {

    private Integer port;

    private RoutingTable routingTable;

    private ReceivePackegeThread receiveThread;

    public Router(Integer porta) {
        this.port = porta;
        this.routingTable = new RoutingTable();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public RoutingTable getRoutingTable() {
        return routingTable;
    }

    public void setRoutingTable(RoutingTable routingTable) {
        this.routingTable = routingTable;
    }

    public ReceivePackegeThread getReceiveThread() {
        return receiveThread;
    }

    public void setReceiveThread(ReceivePackegeThread receiveThread) {
        this.receiveThread = receiveThread;
    }
}
