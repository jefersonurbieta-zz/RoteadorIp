package br.com.urbieta.jeferson.commom;

import br.com.urbieta.jeferson.model.Connection;
import br.com.urbieta.jeferson.model.Package;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class SendPackageThread extends Thread {

    private static final Logger logger = Logger.getLogger(SendPackageThread.class);

    private Connection connection;

    private Package packageToSend;

    private String routerAddress;

    private Integer routerPort;

    public SendPackageThread(String routerAddress, Integer routerPort, Package packageToSend, Connection connection) {
        this.routerAddress = routerAddress;
        this.routerPort = routerPort;
        this.packageToSend = packageToSend;
        this.connection = connection;
    }

    public void run() {
        try {
            InetAddress iPDestino = InetAddress.getByName(routerAddress);
            byte[] sendData = packageToSend.toString().getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, iPDestino, routerPort);
            connection.getServerSocket().send(sendPacket);
        } catch (IOException e) {
            logger.error(e);
        }
    }

}
