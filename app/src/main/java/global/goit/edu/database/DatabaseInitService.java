package global.goit.edu.database;

import org.flywaydb.core.Flyway;

import java.sql.SQLException;

public class DatabaseInitService {
    public static void main(String[] args) {

        Flyway flyway = Flyway.configure()
                .dataSource(
                        "jdbc:h2:../DeveloperHomeworkModule12.app.main/space_travel",
                        null,
                        null
                )
                .load();

        flyway.migrate();

    }
}