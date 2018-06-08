package br.com.urbieta.jeferson.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ScannerService {

    private Scanner scanner;

    public ScannerService() {
        this.scanner = new Scanner(System.in);
    }

    public String getString(String mensagem) {
        System.out.println(mensagem);
        return scanner.nextLine();
    }

    public Integer getInteger(String mensagem) {
        System.out.println(mensagem);
        return scanner.nextInt();
    }

}
