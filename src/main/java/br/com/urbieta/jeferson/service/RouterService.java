package br.com.urbieta.jeferson.service;

import br.com.urbieta.jeferson.exception.ApplicationException;
import br.com.urbieta.jeferson.model.Connection;
import br.com.urbieta.jeferson.model.Redirection;
import br.com.urbieta.jeferson.model.Router;
import br.com.urbieta.jeferson.utils.RouterUtils;
import br.com.urbieta.jeferson.utils.ScannerUtils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RouterService {

    private Map<Integer, Router> routers = new TreeMap<Integer, Router>();

    public void createRouter() {
        Integer routerPort = ScannerUtils.getInteger("Digite a porta do roteador default:");
        String routersForRedirection = ScannerUtils.getString("Digite a lista de endereçõs de encaminhamento:");
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
                ScannerUtils.showMessage("There is already a router running on the port!");
                return;
            }
            Router router = new Router();
            router.setPort(port);
            router.getRoutingTable().addAllRedirections(redirections);
            Connection connection = ConnectionService.getConnection(router.getPort());
            router.setConnection(connection);
            router.start();
            routers.put(router.getPort(), router);
            ScannerUtils.showMessage("Router Created!");
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
            System.out.format("| No router running                |%n");
        }
        System.out.format("+-----------------+----------------+%n");
    }

    public void routerDetail(Integer routerPort) {
        if (routerPort == null) {
            routerPort = ScannerUtils.getInteger("Digite a porta do roteador que deseja visualizar:");
        }
        Router router = routers.get(routerPort);
        if (router == null) {
            ScannerUtils.showMessage("No router running on the port!");
            return;
        }
        String leftAlignFormat = "| %-15s | %-14s | %-14s | %-15s |%n";
        System.out.format("+-----------------+----------------+----------------+-----------------+%n");
        System.out.format("| Destiny         | Mask           | Gateway        | Interface Output|%n");
        System.out.format("+-----------------+----------------+----------------+-----------------+%n");
        if (router.getRoutingTable().getRedirections().size() > 0) {
            for (Redirection redirection : router.getRoutingTable().getRedirections()) {
                System.out.format(leftAlignFormat, redirection.getDestiny(), RouterUtils.formatMaskCIDRNotation(redirection.getMask()), redirection.getGateway(), redirection.getInterfaceOutput());
            }
        } else {
            System.out.format("| No redirection configured on this router                            |%n");
        }
        System.out.format("+-----------------+----------------+----------------+-----------------+%n");
    }

    public void stopRouter(Integer routerPort) {
        if (routerPort == null) {
            routerPort = ScannerUtils.getInteger("Digite a porta do roteador que deseja parar:");
        }
        Router router = routers.get(routerPort);
        if (router == null) {
            ScannerUtils.showMessage("No redirection configured on this router!");
            return;
        }
        router.stop();
        routers.remove(routerPort);
        ScannerUtils.showMessage("Router stopped!");
    }

}
