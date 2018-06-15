package br.com.urbieta.jeferson.commom;

import br.com.urbieta.jeferson.model.entity.Connection;
import br.com.urbieta.jeferson.model.entity.Package;
import br.com.urbieta.jeferson.model.entity.Router;
import org.apache.log4j.Logger;

import java.net.DatagramPacket;
import java.net.InetAddress;


public class RouterProcessingThread extends Thread {

    private static final Logger logger = Logger.getLogger(RouterProcessingThread.class);

    private Connection connection;

    private Router router;

    private DatagramPacket receivePacket;

    RouterProcessingThread(Connection connection, Router router, DatagramPacket receivePacket) {
        this.connection = connection;
        this.router = router;
        this.receivePacket = receivePacket;
    }

    public void run() {
        logger.info("Iniciado Thread Server Receive");
        try {
            String sentence = new String(receivePacket.getData());
            Package packageReceive = new Package(sentence);
            InetAddress addressIP = receivePacket.getAddress();
            Integer port = receivePacket.getPort();
            logger.info("ROUTER " + router.getPort() + " RECEIVED: FROM: " + addressIP.getHostAddress() + ":" + port + " - DATA: " + sentence);
        } catch (Exception e) {
            logger.error(e);
        }
    }

}