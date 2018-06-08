package br.com.urbieta.jeferson.service;

import br.com.urbieta.jeferson.exception.ApplicationException;
import br.com.urbieta.jeferson.model.entity.Connection;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectionService {

    private static final Logger logger = Logger.getLogger(ConnectionService.class);

    private List<Connection> conexoes;

    public ConnectionService() {
        conexoes = new ArrayList<>();
    }

    public Connection getConnection(Integer porta) throws ApplicationException {
        Connection connectionEncontrada = null;
        for (Connection connection : conexoes) {
            if (connection.getPorta().equals(porta)) {
                connectionEncontrada = connection;
                break;
            }
        }
        if (connectionEncontrada == null) {
            connectionEncontrada = createConnection(porta);
        }
        return connectionEncontrada;
    }

    private Connection createConnection(Integer porta) throws ApplicationException {
        try {
            DatagramSocket serverSocket = new DatagramSocket(porta);
            Connection connection = new Connection(porta, serverSocket);
            conexoes.add(connection);
            return connection;
        } catch (SocketException e) {
            logger.error(e);
            throw new ApplicationException(e);
        }
    }
}
