package global.goit.edu.database;;

import org.h2.tools.Server;

import java.sql.SQLException;

public class DBManager {

    private static final DBManager DB_MANAGER = new DBManager();
    private Server server;

    private DBManager() {
        String[] tpcMode = new String[]{"-tcpAllowOthers", "-tcpPort", "8082"};
        try {
            server = Server.createTcpServer(tpcMode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBManager getDbManager() {
        return DB_MANAGER;
    }

    public void startServerDB() throws SQLException {
        server.start();
    }

    public void stopServerDB() {
        server.stop();
    }

}