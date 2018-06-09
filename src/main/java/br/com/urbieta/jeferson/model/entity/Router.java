package br.com.urbieta.jeferson.model.entity;

import br.com.urbieta.jeferson.commom.ReceivePackegeThread;

import java.util.ArrayList;
import java.util.List;

public class Router {

    private Integer port;

    private List<Redirection> routingTable;

    private ReceivePackegeThread receiveThread;

    public Router(Integer porta) {
        this.port = porta;
        this.routingTable = new ArrayList<>();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer porta) {
        this.port = porta;
    }

    public List<Redirection> getRoutingTable() {
        return routingTable;
    }

    public void setRoutingTable(List<Redirection> routingTable) {
        this.routingTable = routingTable;
    }

    public ReceivePackegeThread getReceiveThread() {
        return receiveThread;
    }

    public void setReceiveThread(ReceivePackegeThread receiveThread) {
        this.receiveThread = receiveThread;
    }
}
