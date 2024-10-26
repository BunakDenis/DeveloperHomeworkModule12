package global.goit.edu.database;

import org.flywaydb.core.Flyway;

public class DatabaseForTestsInitService {

    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource(
                        "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
                        null,
                        null
                )
                .load();

        flyway.migrate();
    }

}
