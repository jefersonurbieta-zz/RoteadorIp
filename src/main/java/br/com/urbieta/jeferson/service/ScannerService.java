package br.com.urbieta.jeferson.service;

import java.util.Scanner;

public class ScannerService {

    private static Scanner scanner = new Scanner(System.in);

    public static String getString(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public static Integer getInteger(String message) {
        System.out.println(message);
        String line = scanner.nextLine();
        return Integer.valueOf(line);
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }

}
