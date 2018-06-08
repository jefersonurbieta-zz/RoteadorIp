package br.com.urbieta.jeferson.model.entity;

import java.net.DatagramSocket;

public class Connection {

    private Integer porta;

    private DatagramSocket serverSocket;

    public Connection(Integer porta) {
        this.porta = porta;
    }

    public Connection(Integer porta, DatagramSocket serverSocket) {
        this.porta = porta;
        this.serverSocket = serverSocket;
    }

    public Integer getPorta() {
        return porta;
    }

    public void setPorta(Integer porta) {
        this.porta = porta;
    }

    public DatagramSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(DatagramSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
}
