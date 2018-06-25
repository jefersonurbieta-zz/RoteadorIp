package br.com.urbieta.jeferson.service;

import br.com.urbieta.jeferson.commom.SendPackageThread;
import br.com.urbieta.jeferson.exception.ApplicationException;
import br.com.urbieta.jeferson.model.Connection;
import br.com.urbieta.jeferson.model.Package;

public class EmitterService {

    public void emit() throws ApplicationException {
        String routerAddress = ScannerService.getString("Digite o endereço do roteador default:");
        Integer routerPort = ScannerService.getInteger("Digite a porta do roteador default:");
        String sourceAddress = ScannerService.getString("Digite o endereço de origem:");
        String destinationAddress = ScannerService.getString("Digite o endereço de destino:");
        String message = ScannerService.getString("Digite a mensagem que deseja enviar:");

        emitCommand(routerAddress, sourceAddress, destinationAddress, routerPort, message);
    }

    public void emitCommand(String command) throws ApplicationException {
        String[] parts = command.split(" ");
        String routerAddress = parts[0];
        Integer routerPort = Integer.valueOf(parts[1]);
        String sourceAddress = parts[2];
        String destinationAddress = parts[3];
        String message = parts[4];

        emitCommand(routerAddress, sourceAddress, destinationAddress, routerPort, message);
    }

    public void emitPackage(String routerAddress, Integer routerPort, Package packageToSend) throws ApplicationException {
        Connection connection = ConnectionService.getConnection(routerPort);
        new SendPackageThread(routerAddress, routerPort, packageToSend, connection).start();
    }

    private void emitCommand(String routerAddress, String sourceAddress, String destinationAddress, Integer routerPort, String message) throws ApplicationException {
        Package packageToSend = new Package(sourceAddress, destinationAddress, routerPort, message);
        Connection connection = ConnectionService.getConnection(routerPort);
        new SendPackageThread(routerAddress, routerPort, packageToSend, connection).start();
    }

}
