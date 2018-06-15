package br.com.urbieta.jeferson.model.entity;

import java.util.List;

public class RoutingTable {

    private List<Redirection> routingTable;

    public Integer getCountRedirectionInRoutingTable() {
        return routingTable.size();
    }

    public List<Redirection> getRoutingTable() {
        return routingTable;
    }

    public void setRoutingTable(List<Redirection> routingTable) {
        this.routingTable = routingTable;
    }
}
