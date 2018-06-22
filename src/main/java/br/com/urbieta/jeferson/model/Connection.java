package br.com.urbieta.jeferson.model;

import br.com.urbieta.jeferson.exception.ApplicationException;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Connection {

    private Integer port;

    private DatagramSocket serverSocket;

    public Connection(Integer port) throws ApplicationException {
        try {
            this.port = port;
            this.serverSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new ApplicationException(e);
        }
    }

    public void close() {
        this.serverSocket.close();
    }

    /*
     * Getters e Setters
     */

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public DatagramSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(DatagramSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
}
