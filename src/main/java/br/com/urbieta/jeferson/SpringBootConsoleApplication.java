package br.com.urbieta.jeferson;

import br.com.urbieta.jeferson.service.CommandService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Scanner;

import static java.lang.System.exit;

@SpringBootApplication
@ComponentScan(basePackages = {"br.com.urbieta.jeferson"})
public class SpringBootConsoleApplication implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(SpringBootConsoleApplication.class);

    @Autowired
    private CommandService commandService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringBootConsoleApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) {
        try {
            commandService.showHelp();
            Scanner scan = new Scanner(System.in);
            while (scan.hasNext()) {
                String line = scan.nextLine();
                commandService.executeCommand(line);
            }
        } catch (Exception e) {
            logger.error(e);
        }
        exit(0);
    }
}
