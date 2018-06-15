package br.com.urbieta.jeferson.service;

import br.com.urbieta.jeferson.commom.ReceivePackegeThread;
import br.com.urbieta.jeferson.exception.ApplicationException;
import br.com.urbieta.jeferson.model.entity.Connection;
import br.com.urbieta.jeferson.model.entity.Redirection;
import br.com.urbieta.jeferson.model.entity.Router;
import br.com.urbieta.jeferson.utils.RouterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RouterService {

    @Autowired
    ScannerService scannerService;

    @Autowired
    ConnectionService connectionService;

    private Map<Integer, Router> routers = new TreeMap<>();

    public void createRouter() {
        Integer routerPort = scannerService.getInteger("Digite a porta do roteador default:");
        String routersForRedirection = scannerService.getString("Digite a lista de endereçõs de encaminhamento:");
        Router router = new Router(routerPort);
        List<Redirection> redirections = RouterUtils.formattingRoutingTable(routersForRedirection);
        router.getRoutingTable().setRoutingTable(redirections);
        createRouter(router);
    }

    public void createRouterCommand(String command) {
        String[] parts = command.split(" ");
        Integer routerPort = Integer.valueOf(parts[0]);
        Router router = new Router(routerPort);
        List<Redirection> redirections = RouterUtils.formattingRoutingTableFromCommand(parts);
        router.getRoutingTable().setRoutingTable(redirections);
        createRouter(router);
    }

    private void createRouter(Router router) {
        try {
            if (routers.get(router.getPort()) != null) {
                scannerService.showMessage("Já existem um roteador sendo executado nessa porta!");
                return;
            }
            startRouterThread(router.getPort(), router);
            routers.put(router.getPort(), router);
            scannerService.showMessage("Roteador Criado com sucesso!");
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    private void startRouterThread(Integer routerPort, Router router) throws ApplicationException {
        Connection connection = connectionService.getConnection(routerPort);
        ReceivePackegeThread receivePackegeThread = new ReceivePackegeThread(connection, router);
        receivePackegeThread.start();
        router.setReceiveThread(receivePackegeThread);
    }

    public void routerList() {
        String leftAlignFormat = "| %-15s | %-14s |%n";
        System.out.format("+-----------------+----------------+%n");
        System.out.format("| Router Port     | Redirections   |%n");
        System.out.format("+-----------------+----------------+%n");
        if (!routers.isEmpty()) {
            for (Map.Entry<Integer, Router> entry : routers.entrySet()) {
                System.out.format(leftAlignFormat, entry.getValue().getPort(), entry.getValue().getRoutingTable().getCountRedirectionInRoutingTable());
            }
        } else {
            System.out.format("| Nenhum roteador sendo executado  |%n");
        }
        System.out.format("+-----------------+----------------+%n");
    }

    public void routerDetail() {
        Integer routerPort = scannerService.getInteger("Digite a porta do roteador que deseja visualizar:");
        Router router = routers.get(routerPort);
        if (router == null) {
            scannerService.showMessage("Nenhum roteador sendo execultado nessa porta!");
            return;
        }
        String leftAlignFormat = "| %-15s | %-14s | %-14s | %-15s |%n";
        System.out.format("+-----------------+----------------+----------------+-----------------+%n");
        System.out.format("| Destino         | Mascara        | Gateway        | Interface Saida |%n");
        System.out.format("+-----------------+----------------+----------------+-----------------+%n");
        if (router.getRoutingTable().getCountRedirectionInRoutingTable() > 0) {
            for (Redirection redirection : router.getRoutingTable().getRoutingTable()) {
                System.out.format(leftAlignFormat, redirection.getDestiny(), RouterUtils.formatMaskCIDRNotation(redirection.getMask()), redirection.getGateway(), redirection.getInterfaceOutput());
            }
        } else {
            System.out.format("| Nenhum registro de redirecionamento configurado neste roteador      |%n");
        }
        System.out.format("+-----------------+----------------+----------------+-----------------+%n");
    }

    public void stopRouter() {
        Integer routerPort = scannerService.getInteger("Digite a porta do roteador que deseja parar:");
        Router router = routers.get(routerPort);
        if (router == null) {
            scannerService.showMessage("Nenhum roteador sendo execultado nessa porta!");
            return;
        }
        router.getReceiveThread().setRunning(false);
        connectionService.closeConnection(router.getPort());
        routers.remove(routerPort);
        scannerService.showMessage("Roteador parado!");
    }

}
