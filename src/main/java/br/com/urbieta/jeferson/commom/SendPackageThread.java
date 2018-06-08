package br.com.urbieta.jeferson.commom;

import br.com.urbieta.jeferson.model.entity.Connection;
import br.com.urbieta.jeferson.model.entity.Package;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;


public class SendPackageThread extends Thread {

    private static final Logger logger = Logger.getLogger(SendPackageThread.class);

    private Connection connection;

    private Package aPackage;

    public SendPackageThread(Package aPackage, Connection connection) {
        this.aPackage = aPackage;
        this.connection = connection;
    }

    public void run() {
        try {
            byte[] sendData = aPackage.getMensagem().getBytes();
            InetAddress iPDestino = InetAddress.getByName(aPackage.getRoteador());
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, iPDestino, aPackage.getPorta());
            connection.getServerSocket().send(sendPacket);
            logger.info(" SENT: " + aPackage.getMensagem() + " - BYTES: " + aPackage.getMensagem().getBytes().length);
        } catch (IOException e) {
            logger.error(e);
        }
    }

}