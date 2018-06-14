package br.com.urbieta.jeferson.service;

import br.com.urbieta.jeferson.exception.ApplicationException;
import br.com.urbieta.jeferson.model.enumeration.EnumCommands;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandService {

    private static final Logger logger = Logger.getLogger(CommandService.class);

    @Autowired
    private RouterService routerService;

    @Autowired
    private EmitterService emitterService;

    public void executeCommand(String commandString) {
        try {
            EnumCommands command = identifyCommand(commandString);
            if (command == null) {
                showHelp();
            } else {
                switch (command) {
                    case EMIT_MESSAGE:
                        emitterService.emit();
                        break;
                    case ROUTER_LIST:
                        routerService.routerList();
                        break;
                    case ROUTER_DETAIL:
                        routerService.routerDetail();
                        break;
                    case CREATE_ROUTER:
                        routerService.createRouter();
                        break;
                    case STOP_ROUTER:
                        routerService.stopRouter();
                        break;
                    case HELP:
                        showHelp();
                        break;
                }
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
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
        String leftAlignFormat = "| %-7s | %-14s | %-65s |%n";
        System.out.format("+---------+----------------+-------------------------------------------------------------------+%n");
        System.out.format("| Command | Name           | Description                                                       |%n");
        System.out.format("+---------+----------------+-------------------------------------------------------------------+%n");
        for (EnumCommands enumCommand : EnumCommands.values()) {
            System.out.format(leftAlignFormat, enumCommand.getIndex(), enumCommand.name(), enumCommand.getDescricao());
        }
        System.out.format("+---------+----------------+-------------------------------------------------------------------+%n");
    }
}
