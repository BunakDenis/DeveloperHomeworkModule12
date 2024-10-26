package global.goit.edu;

import global.goit.edu.client.Client;
import global.goit.edu.client.ClientCrudService;
import global.goit.edu.database.DBManager;
import global.goit.edu.database.DatabaseInitService;

import java.sql.*;

public class Tests {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //DatabaseInitService.main(null);

/*        String[] tpcMode = new String[]{"-tcpAllowOthers"};
        Server server = Server.createTcpServer(tpcMode);
        server.start();
        ClientCrudService clientCrudService = new ClientCrudService();
        clientCrudService.getAll().forEach(System.out::println);*/

        //DatabaseInitService.main(null);

/*        ClientCrudService clientCrudService = new ClientCrudService();

        clientCrudService.getAll().forEach(System.out::println);*/

        System.out.println(System.getProperty("user.dir"));

    }
}