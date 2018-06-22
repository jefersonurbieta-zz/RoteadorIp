package br.com.urbieta.jeferson.commom;

import br.com.urbieta.jeferson.model.Package;
import br.com.urbieta.jeferson.model.Router;
import org.apache.log4j.Logger;

import java.net.DatagramPacket;
import java.net.InetAddress;


public class RouterProcessingThread extends Thread {

    private static final Logger logger = Logger.getLogger(RouterProcessingThread.class);

    private Router router;

    private DatagramPacket receivePacket;

    RouterProcessingThread(Router router, DatagramPacket receivePacket) {
        this.router = router;
        this.receivePacket = receivePacket;
    }

    public void run() {
        logger.info("Iniciado Thread Server Receive");
        try {
            String sentence = new String(receivePacket.getData(), "UTF-8");
            Package packageReceive = new Package(sentence);
            router.processReceipt(packageReceive);

            InetAddress addressIP = receivePacket.getAddress();
            Integer port = receivePacket.getPort();
            logger.info("ROUTER " + router.getPort() + " RECEIVED: FROM: " + addressIP.getHostAddress() + ":" + port + " - DATA: " + sentence);
        } catch (Exception e) {
            logger.error(e);
        }
    }

}