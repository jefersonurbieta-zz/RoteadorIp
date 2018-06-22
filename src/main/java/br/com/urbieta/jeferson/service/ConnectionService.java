package br.com.urbieta.jeferson.service;

import br.com.urbieta.jeferson.exception.ApplicationException;
import br.com.urbieta.jeferson.model.Connection;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConnectionService {

    private static final Logger logger = Logger.getLogger(ConnectionService.class);

    private Map<Integer, Connection> connections = new HashMap<>();

    public Connection getConnection(Integer port) throws ApplicationException {
        Connection connectionEncontrada = connections.get(port);
        if (connectionEncontrada == null) {
            connectionEncontrada = createConnection(port);
        }
        return connectionEncontrada;
    }

    private Connection createConnection(Integer port) throws ApplicationException {
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

    public void closeConnection(Integer port) {
        Connection connection = connections.get(port);
        if (connection != null) {
            connection.close();
            connections.remove(port);
        }
    }
}
