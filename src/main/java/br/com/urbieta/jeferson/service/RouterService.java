package br.com.urbieta.jeferson.service;

import br.com.urbieta.jeferson.exception.ApplicationException;
import br.com.urbieta.jeferson.model.Connection;
import br.com.urbieta.jeferson.model.Redirection;
import br.com.urbieta.jeferson.model.Router;
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
        String[] routersForRedirectionInParts = routersForRedirection.split(" ");
        List<Redirection> redirections = RouterUtils.formattingRoutingTableFromCommand(routersForRedirectionInParts);
        createRouter(routerPort, redirections);
    }

    public void createRouterCommand(String command) {
        String[] parts = command.split(" ");
        Integer routerPort = Integer.valueOf(parts[0]);
        List<Redirection> redirections = RouterUtils.formattingRoutingTableFromCommand(parts);
        createRouter(routerPort, redirections);
    }

    private void createRouter(Integer port, List<Redirection> redirections) {
        try {
            if (routers.get(port) != null) {
                scannerService.showMessage("Já existem um roteador sendo executado nessa porta!");
                return;
            }
            Router router = new Router();
            router.setPort(port);
            router.getRoutingTable().addAllRedirections(redirections);
            Connection connection = connectionService.getConnection(router.getPort());
            router.setConnection(connection);
            router.start();
            routers.put(router.getPort(), router);
            scannerService.showMessage("Roteador Criado com sucesso!");
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    public void routerList() {
        String leftAlignFormat = "| %-15s | %-14s |%n";
        System.out.format("+-----------------+----------------+%n");
        System.out.format("| Router Port     | Redirections   |%n");
        System.out.format("+-----------------+----------------+%n");
        if (!routers.isEmpty()) {
            for (Map.Entry<Integer, Router> entry : routers.entrySet()) {
                System.out.format(leftAlignFormat, entry.getValue().getPort(), entry.getValue().getRoutingTable().getRedirections().size());
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
        if (router.getRoutingTable().getRedirections().size() > 0) {
            for (Redirection redirection : router.getRoutingTable().getRedirections()) {
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
