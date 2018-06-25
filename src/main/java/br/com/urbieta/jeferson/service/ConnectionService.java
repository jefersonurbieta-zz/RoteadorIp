package br.com.urbieta.jeferson.service;

import br.com.urbieta.jeferson.exception.ApplicationException;
import br.com.urbieta.jeferson.model.Connection;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ConnectionService {

    private static final Logger logger = Logger.getLogger(ConnectionService.class);

    private static Map<Integer, Connection> connections = new HashMap<Integer, Connection>();

    public static Connection getConnection(Integer port) throws ApplicationException {
        Connection connectionEncontrada = connections.get(port);
        if (connectionEncontrada == null) {
            connectionEncontrada = createConnection(port);
        }
        return connectionEncontrada;
    }

    private static Connection createConnection(Integer port) throws ApplicationException {
        try {
            Connection connection = connections.get(port);
            if (connection == null) {
                connection = new Connection(port);
                connections.put(port, connection);
            }
            return connection;
        } catch (ApplicationException e) {
            logger.error(e);
            throw new ApplicationException(e);
        }
    }

    public static void closeConnection(Integer port) {
        Connection connection = connections.get(port);
        if (connection != null) {
            connection.close();
            connections.remove(port);
        }
    }
}
