package br.com.urbieta.jeferson.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RoutingTable {

    private List<Redirection> redirections;

    public RoutingTable() {
        this.redirections = new ArrayList<Redirection>();
    }

    public void addAllRedirections(List<Redirection> redirections) {
        this.redirections.addAll(redirections);
    }

    public Redirection findRouterToForward(String packageDestiny) {
        List<Redirection> combinedRedirects = new ArrayList<Redirection>();
        for (Redirection redirection : redirections) {
            if (redirectionMatch(redirection, packageDestiny)) {
                combinedRedirects.add(redirection);
            }
        }

        if (combinedRedirects.isEmpty()) {
            return null;
        } else if (combinedRedirects.size() == 1) {
            return combinedRedirects.get(0);
        } else {
            return findRedirectionWithLongestMatch(combinedRedirects);
        }
    }

    private boolean redirectionMatch(Redirection redirection, String packageDestiny) {
        String[] packageDestinyParts = packageDestiny.split(Pattern.quote("."));
        String[] redirectionDestinyParts = redirection.getDestiny().split(Pattern.quote("."));

        boolean match = true;
        if (redirection.getMask() >= 8 && !packageDestinyParts[0].equals(redirectionDestinyParts[0])) {
            match = false;
        }
        if (redirection.getMask() >= 16 && !packageDestinyParts[1].equals(redirectionDestinyParts[1])) {
            match = false;
        }
        if (redirection.getMask() >= 24 && !packageDestinyParts[2].equals(redirectionDestinyParts[2])) {
            match = false;
        }
        if (redirection.getMask() >= 32 && !packageDestinyParts[3].equals(redirectionDestinyParts[3])) {
            match = false;
        }

        return match;
    }

    private Redirection findRedirectionWithLongestMatch(List<Redirection> combinedRedirects) {
        Redirection redirectionWithLongestMatch = combinedRedirects.get(0);
        for (Redirection redirection : combinedRedirects) {
            // Quando tem um redirecionamento direto na lista
            if (redirection.getGateway().equals("0.0.0.0") && redirection.getInterfaceOutput() == 0) {
                redirectionWithLongestMatch = redirection;
                break;
            }
            // Procura o redirecionamento com maior mascara
            if (redirection.getMask() > redirectionWithLongestMatch.getMask()) {
                redirectionWithLongestMatch = redirection;
            }
        }
        return redirectionWithLongestMatch;
    }

    /*
     * Getters e Setters
     */

    public List<Redirection> getRedirections() {
        return redirections;
    }
}
