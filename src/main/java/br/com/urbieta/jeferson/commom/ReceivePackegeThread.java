package br.com.urbieta.jeferson.commom;

import br.com.urbieta.jeferson.model.entity.Connection;
import br.com.urbieta.jeferson.model.entity.Router;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;


public class ReceivePackegeThread extends Thread {

    private static final Logger logger = Logger.getLogger(ReceivePackegeThread.class);

    private Connection connection;

    private Router router;

    private boolean running;

    public void run() {
        logger.info("Iniciado Thread Server Receive");
        try {
            while (running) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                connection.getServerSocket().receive(receivePacket);
//                new RCAServerProcessRequisition(serverSocket, preferences, receivePacket).start();
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }
}