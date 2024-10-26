package global.goit.edu.hibernateservice;

import global.goit.edu.client.Client;
import global.goit.edu.planet.PlanetService;
import global.goit.edu.ticket.Ticket;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateService {
    private static final HibernateService INSTANCE;

    @Getter
    private SessionFactory sessionFactory;

    static {
        INSTANCE = new HibernateService();
    }

    private HibernateService() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(PlanetService.class)
                .addAnnotatedClass(Ticket.class)
                .buildSessionFactory();
    }

    public static HibernateService getInstance() {
        return INSTANCE;
    }

    public void close() {
        sessionFactory.close();
    }
}