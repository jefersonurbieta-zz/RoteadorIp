package br.com.urbieta.jeferson.service;

import br.com.urbieta.jeferson.exception.ApplicationException;
import br.com.urbieta.jeferson.model.entity.Connection;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConnectionService {

    private static final Logger logger = Logger.getLogger(ConnectionService.class);

    private Map<Integer, Connection> conexoes = new HashMap<>();

    public Connection getConnection(Integer porta) throws ApplicationException {
        Connection connectionEncontrada = conexoes.get(porta);
        if (connectionEncontrada == null) {
            connectionEncontrada = createConnection(porta);
        }
        return connectionEncontrada;
    }

    private Connection createConnection(Integer port) throws ApplicationException {
        try {
            Connection connection = conexoes.get(port);
            if (connection == null) {
                DatagramSocket serverSocket = new DatagramSocket(port);
                connection = new Connection(port, serverSocket);
                conexoes.put(port, connection);
            }
            return connection;
        } catch (SocketException e) {
            logger.error(e);
            throw new ApplicationException(e);
        }
    }

    public void closeConnection(Integer port) {
        Connection connection = conexoes.get(port);
        if (connection != null) {
            connection.getServerSocket().close();
            conexoes.remove(port);
        }
    }
}
