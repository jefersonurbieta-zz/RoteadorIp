package br.com.urbieta.jeferson.commom;

import br.com.urbieta.jeferson.model.Connection;
import br.com.urbieta.jeferson.model.Router;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;


public class ReceivePackegeThread extends Thread {

    private static final Logger logger = Logger.getLogger(ReceivePackegeThread.class);

    private Connection connection;

    private Router router;

    private boolean running;

    public ReceivePackegeThread() {
        this.running = true;
    }

    public ReceivePackegeThread(Connection connection, Router router) {
        this.connection = connection;
        this.router = router;
        this.running = true;
    }

    public void run() {
        logger.info("Iniciado Thread Server Receive");
        try {
            while (running) {
                byte[] receiveData = new byte[65508];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                connection.getServerSocket().receive(receivePacket);
                new RouterProcessingThread(router, receivePacket).start();
            }
        } catch (IOException | NullPointerException e) {
            logger.error(e);
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setRouter(Router router) {
        this.router = router;
    }
}
