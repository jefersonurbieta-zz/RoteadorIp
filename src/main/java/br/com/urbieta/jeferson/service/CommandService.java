package br.com.urbieta.jeferson.service;

import br.com.urbieta.jeferson.model.enumeration.EnumCommands;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CommandService {

    private static final Logger logger = Logger.getLogger(CommandService.class);

    @Autowired
    private RouterService routerService;


    public void executeCommand(String commandString) {
    	EnumCommands command = identifyCommand(commandString);
        if (command == null) {
            showHelp();
        } else {
            System.out.println(command.name());
        }
    }

    private EnumCommands identifyCommand(String command) {

        if (command.length() == 1) {
            Integer index = Integer.valueOf(command);
            return getCommandByIndex(index);
        } else {
            try {
                String formattedCommand = command.replace(" ", "_");
                return EnumCommands.valueOf(formattedCommand.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    private EnumCommands getCommandByIndex(Integer index) {
        for (EnumCommands enumCommands : EnumCommands.values()) {
            if (enumCommands.getIndex().equals(index)) {
                return enumCommands;
            }
        }
        return null;
    }

    public void showHelp() {
        for (EnumCommands enumCommand : EnumCommands.values()) {
            System.out.println(enumCommand.getIndex() + ". " + enumCommand.name() + " - " + enumCommand.getDescricao());
        }
    }
}
