package br.com.urbieta.jeferson.service;

import br.com.urbieta.jeferson.model.entity.Redirection;
import br.com.urbieta.jeferson.model.entity.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RouterService {

	@Autowired
	ScannerService scannerService;

	@Autowired
	ConnectionService connectionService;

	private Map<Integer, Router> routers;
	

	public RouterService() {
		routers = new TreeMap<>();
	}

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
		String leftAlignFormat = "| %-15s | %-14d |%n";
		System.out.format("+-----------------+----------------+%n");
		System.out.format("| Router Port     | Redirections   |%n");
		System.out.format("+-----------------+----------------+%n");
		for (Map.Entry<Integer, Router> entry : routers.entrySet()) {
			System.out.format(leftAlignFormat, entry.getValue().getPort(), entry.getValue().getRoutingTable().size());
        }
		System.out.format("+-----------------+----------------+%n");
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
			redirection.setDestino(parts[0]);
			redirection.setMascara(formatMaskCIDRNotation(parts[1]));
			redirection.setGateway(parts[2]);
			redirection.setInterfaceSaida(Integer.valueOf(parts[3]));
			redirections.add(redirection);
		}
		return redirections;
	}

	private Integer formatMaskCIDRNotation(String mask) {
		Integer cidr = 0;
		if (mask.contains(".")) {
			String[] parts = mask.split(".");
			for (String part : parts) {
				if (part.equals("255")) {
					cidr += 8;
				}
			}
		} else {
			cidr = Integer.parseInt(mask);
		}
		return cidr;
	}

}
