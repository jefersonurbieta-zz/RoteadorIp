package br.com.urbieta.jeferson.service;

import br.com.urbieta.jeferson.model.entity.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouterService {

    @Autowired
    ScannerService scannerService;

    @Autowired
    ConnectionService connectionService;

    private List<Router> routers;

    public void createRouter(){
        Integer routerPort = scannerService.getInteger("Digite a porta do roteador default:");
    }

}
