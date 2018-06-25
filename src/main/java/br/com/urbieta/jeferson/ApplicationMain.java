package br.com.urbieta.jeferson;

import br.com.urbieta.jeferson.service.CommandService;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class ApplicationMain {

    private static final Logger logger = Logger.getLogger(ApplicationMain.class);

    public static void main(String[] args) {
        try {
            CommandService commandService = new CommandService();
            commandService.showHelp();
            Scanner scan = new Scanner(System.in);
            while (scan.hasNext()) {
                String line = scan.nextLine();
                commandService.executeCommand(line);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

}
