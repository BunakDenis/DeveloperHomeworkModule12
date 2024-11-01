package global.goit.edu.database;

import org.flywaydb.core.Flyway;

import java.sql.SQLException;

public class DatabaseInitService {
    public static void main(String[] args) {
//"jdbc:h2:file:./app/src/main/webapp/db/space_travel"
        Flyway flyway = Flyway.configure()
                .dataSource(
                        "jdbc:h2:" + args[0],
                        null,
                        null
                )
                .load();
        flyway.migrate();
    }
}