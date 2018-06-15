package br.com.urbieta.jeferson.utils;

import br.com.urbieta.jeferson.model.entity.Redirection;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RouterUtils {

    private static Integer formatMaskCIDRNotation(String mask) {
        Integer cidr = 0;
        if (mask.contains(".")) {
            String[] parts = mask.split(Pattern.quote("."));
            for (String part : parts) {
                if (part.contains("255")) {
                    cidr += 8;
                }
            }
        } else {
            cidr = Integer.parseInt(mask);
        }
        return cidr;
    }

    public static String formatMaskCIDRNotation(Integer numberInCIDNotation) {
        switch (numberInCIDNotation) {
            case 0:
                return "0.0.0.0";
            case 8:
                return "255.0.0.0";
            case 16:
                return "255.255.0.0";
            case 24:
                return "255.255.255.0";
            case 32:
                return "255.255.255.255";
            default:
                return "255.255.255.255";
        }
    }

    public static List<Redirection> formattingRoutingTableFromCommand(String[] routersForRedirection) {
        String routersForRedirectionList = "";
        for (String redirection : routersForRedirection) {
            if (redirection.contains("/")) {
                routersForRedirectionList = routersForRedirectionList + redirection + " ";
            }
        }
        return formattingRoutingTable(routersForRedirectionList);
    }

    public static List<Redirection> formattingRoutingTable(String routersForRedirection) {
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
