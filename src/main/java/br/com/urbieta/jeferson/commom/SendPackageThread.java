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

    private Package packageToSend;

    public SendPackageThread(Package packageToSend, Connection connection) {
        this.packageToSend = packageToSend;
        this.connection = connection;
    }

    public void run() {
        try {
            InetAddress iPDestino = InetAddress.getByName(packageToSend.getRoteador());
            byte[] sendData = packageToSend.toString().getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, iPDestino, packageToSend.getPorta());
            connection.getServerSocket().send(sendPacket);
            logger.info(" SENT: " + packageToSend.getMensagem() + " - BYTES: " + packageToSend.getMensagem().getBytes().length);
        } catch (IOException e) {
            logger.error(e);
        }
    }

}