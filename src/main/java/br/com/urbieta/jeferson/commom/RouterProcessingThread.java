package br.com.urbieta.jeferson.commom;

import br.com.urbieta.jeferson.model.entity.Connection;
import br.com.urbieta.jeferson.model.entity.Router;
import org.apache.log4j.Logger;

import java.net.DatagramPacket;


public class RouterProcessingThread extends Thread {

    private static final Logger logger = Logger.getLogger(RouterProcessingThread.class);

    private Connection connection;

    private Router router;

    private DatagramPacket receivePacket;

    public RouterProcessingThread(Connection connection, Router router, DatagramPacket receivePacket) {
        this.connection = connection;
        this.router = router;
        this.receivePacket = receivePacket;
    }

    public void run() {
        logger.info("Iniciado Thread Server Receive");

    }

}