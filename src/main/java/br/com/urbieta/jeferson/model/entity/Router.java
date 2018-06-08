package br.com.urbieta.jeferson.model.entity;

import br.com.urbieta.jeferson.commom.ReceivePackegeThread;

import java.util.ArrayList;
import java.util.List;

public class Router {

    private Integer porta;

    private List<Redirection> tabelaRoteamento;

    private ReceivePackegeThread receiveThread;

    public Router(Integer porta) {
        this.porta = porta;
        this.tabelaRoteamento = new ArrayList<>();
    }

    public Integer getPorta() {
        return porta;
    }

    public void setPorta(Integer porta) {
        this.porta = porta;
    }

    public List<Redirection> getTabelaRoteamento() {
        return tabelaRoteamento;
    }

    public void setTabelaRoteamento(List<Redirection> tabelaRoteamento) {
        this.tabelaRoteamento = tabelaRoteamento;
    }

    public ReceivePackegeThread getReceiveThread() {
        return receiveThread;
    }

    public void setReceiveThread(ReceivePackegeThread receiveThread) {
        this.receiveThread = receiveThread;
    }
}
