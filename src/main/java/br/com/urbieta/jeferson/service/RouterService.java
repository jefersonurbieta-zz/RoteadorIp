package br.com.urbieta.jeferson.service;

import br.com.urbieta.jeferson.model.entity.Redirection;
import br.com.urbieta.jeferson.model.entity.Router;
import br.com.urbieta.jeferson.utils.RouterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

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
        Router router = routers.get(routerPort);
        if (router != null) {
            scannerService.showMessage("Já existem um roteador sendo executado nessa porta!");
            return;
        }
        router = new Router(routerPort);
        router.setRoutingTable(formattingRoutingTable(routersForRedirection));
        routers.put(routerPort, router);
        scannerService.showMessage("Roteador Criado com sucesso!");
    }

    public void routerList() {
        String leftAlignFormat = "| %-15s | %-14s |%n";
        System.out.format("+-----------------+----------------+%n");
        System.out.format("| Router Port     | Redirections   |%n");
        System.out.format("+-----------------+----------------+%n");
        if (!routers.isEmpty()) {
            for (Map.Entry<Integer, Router> entry : routers.entrySet()) {
                System.out.format(leftAlignFormat, entry.getValue().getPort(), entry.getValue().getRoutingTable().size());
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
        if (!router.getRoutingTable().isEmpty()) {
            for (Redirection redirection : router.getRoutingTable()) {
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
//        router.getReceiveThread().setRunning(false);
        routers.remove(routerPort);
        scannerService.showMessage("Roteador parado!");
    }

    private List<Redirection> formattingRoutingTable(String routersForRedirection) {
        List<Redirection> redirections = new ArrayList<>();
        String[] routers = routersForRedirection.split(" ");
        for (String router : routers) {
            String[] parts = router.split("/");
            if (parts.length != 4) {
                continue;
            }
            Redirection redirection = new Redirection();
            redirection.setDestiny(parts[0]);
            redirection.setMask(RouterUtils.formatMaskCIDRNotation(parts[1]));
            redirection.setGateway(parts[2]);
            redirection.setInterfaceOutput(Integer.valueOf(parts[3]));
            redirections.add(redirection);
        }
        return redirections;
    }

}
