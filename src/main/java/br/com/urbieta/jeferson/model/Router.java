package br.com.urbieta.jeferson.model;

import br.com.urbieta.jeferson.commom.ReceivePackegeThread;
import br.com.urbieta.jeferson.exception.ApplicationException;
import br.com.urbieta.jeferson.service.EmitterService;
import br.com.urbieta.jeferson.service.ScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Router {

    @Autowired
    private ScannerService scannerService;

    @Autowired
    private EmitterService emitterService;

    private Integer port;

    private RoutingTable routingTable;

    private Connection connection;

    private ReceivePackegeThread receiveThread;

    public Router() {
        this.routingTable = new RoutingTable();
        this.receiveThread = new ReceivePackegeThread();
    }

    public void start() throws ApplicationException {
        if (port == null) {
            throw new ApplicationException("Port not specified");
        }
        this.receiveThread.setConnection(this.connection);
        this.receiveThread.setRouter(this);
        this.receiveThread.start();
    }

    public void processReceipt(Package packageReceive) throws ApplicationException {
        packageReceive.decreaseTLL();
        // Caso o pacote exceda o TTL, será descartado
        if (packageReceive.getTll() == 0) {
            String message = "Time to Live exceeded in Transit, dropping packet for " + packageReceive.getDestinationAddress();
            System.out.println(message);
            return;
        }

        Redirection redirection = routingTable.findRouterToForward(packageReceive.getDestinationAddress());

        // Caso não existe casamento para o destino na tabela de roteamento
        if (redirection == null) {
            String message = "Destination " + packageReceive.getDestinationAddress() + " not found in routing table, dropping packet";
            System.out.println(message);
            return;
        }

        // Caso seja recebido um pacote destinado a um host  da rede local (roteamento direto)
        if (redirection.isDirect()) {
            String message = "Destination reached. From " + packageReceive.getSourceAddress()
                    + " to " + packageReceive.getDestinationAddress()
                    + " : " + packageReceive.getMessage();
            System.out.println(message);
            return;
        }

        // Caso seja recebido um pacote destinado a outro roteador
        if (redirection.isDirect()) {
            emitterService.emitPackage(redirection.getGateway(), redirection.getInterfaceOutput(), packageReceive);
            String message = "Forwarding packet for " + packageReceive.getDestinationAddress()
                    + " to next hop " + redirection.getGateway()
                    + " over interface " + redirection.getInterfaceOutput();
            System.out.println(message);
        }
    }

    /*
     * Getters e Setters
     */

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public RoutingTable getRoutingTable() {
        return routingTable;
    }

    public void setRoutingTable(RoutingTable routingTable) {
        this.routingTable = routingTable;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public ReceivePackegeThread getReceiveThread() {
        return receiveThread;
    }

    public void setReceiveThread(ReceivePackegeThread receiveThread) {
        this.receiveThread = receiveThread;
    }
}
