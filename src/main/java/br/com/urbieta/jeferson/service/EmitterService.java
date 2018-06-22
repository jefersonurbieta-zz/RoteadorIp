package br.com.urbieta.jeferson.service;

import br.com.urbieta.jeferson.commom.SendPackageThread;
import br.com.urbieta.jeferson.exception.ApplicationException;
import br.com.urbieta.jeferson.model.Connection;
import br.com.urbieta.jeferson.model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmitterService {

    @Autowired
    ScannerService scannerService;

    @Autowired
    ConnectionService connectionService;

    public void emit() throws ApplicationException {
        String routerAddress = scannerService.getString("Digite o endereço do roteador default:");
        Integer routerPort = scannerService.getInteger("Digite a porta do roteador default:");
        String sourceAddress = scannerService.getString("Digite o endereço de origem:");
        String destinationAddress = scannerService.getString("Digite o endereço de destino:");
        String message = scannerService.getString("Digite a mensagem que deseja enviar:");

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
        Connection connection = connectionService.getConnection(routerPort);
        new SendPackageThread(routerAddress, routerPort, packageToSend, connection).start();
    }

    private void emitCommand(String routerAddress, String sourceAddress, String destinationAddress, Integer routerPort, String message) throws ApplicationException {
        Package packageToSend = new Package(sourceAddress, destinationAddress, routerPort, message);
        Connection connection = connectionService.getConnection(routerPort);
        new SendPackageThread(routerAddress, routerPort, packageToSend, connection).start();
    }

}
