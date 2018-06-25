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
            if (redirectionMatch2(redirection, packageDestiny)) {
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
    
    private boolean redirectionMatch2(Redirection redirection, String packageDestiny) {
    	String[] packageDestinyParts = packageDestiny.split(Pattern.quote("."));
        String[] redirectionDestinyParts = redirection.getDestiny().split(Pattern.quote("."));
        
        String packageDestinyBinary = "";
        for(String part : packageDestinyParts) {
        	packageDestinyBinary += getBinaryFromInt(Integer.parseInt(part));
        }
        
        String redirectionDestinyBinary = "";
        for(String part : redirectionDestinyParts) {
        	redirectionDestinyBinary += getBinaryFromInt(Integer.parseInt(part));
        }
        
        packageDestinyBinary = packageDestinyBinary.substring(0, redirection.getMask());
        redirectionDestinyBinary = redirectionDestinyBinary.substring(0, redirection.getMask());
        
        if(redirection.getMask() == 0) {
        	return true;
        }
        
        return packageDestinyBinary.equals(redirectionDestinyBinary);
    }

    private Redirection findRedirectionWithLongestMatch(List<Redirection> combinedRedirects) {
        Redirection redirectionWithLongestMatch = combinedRedirects.get(0);
        for (Redirection redirection : combinedRedirects) {
            // Procura o redirecionamento com maior mascara
            if (redirection.getMask() > redirectionWithLongestMatch.getMask()) {
                redirectionWithLongestMatch = redirection;
            }
        }
        return redirectionWithLongestMatch;
    }

    private String getBinaryFromInt(Integer part) {
    	String binary = Integer.toBinaryString(part);
    	if(binary.length() < 8) {
    		switch (8 - binary.length()) {
			case 7:
				binary = "0000000" + binary;
				break;
			case 6:
				binary = "000000" + binary;
				break;
			case 5:
				binary = "00000" + binary;
				break;
			case 4:
				binary = "0000" + binary;
				break;
			case 3:
				binary = "000" + binary;
				break;
			case 2:
				binary = "00" + binary;
				break;
			case 1:
				binary = "0" + binary;
				break;
			}
    	}
    	return binary;
    }
    /*
     * Getters e Setters
     */

    public List<Redirection> getRedirections() {
        return redirections;
    }
}
