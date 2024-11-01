package global.goit.edu.planet;

import global.goit.edu.hibernateservice.HibernateService;
import global.goit.edu.ticket.Ticket;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PlanetCrudService {

    private SessionFactory sessionFactory;

    public PlanetCrudService() {
        sessionFactory = HibernateService.getInstance().getSessionFactory();
    }

    public boolean save(Planets planet) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(PlanetService.builder()
                    .id(planet.toString())
                    .name(planet.name)
                    .build()
            );
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Planets> getAll() {
        List<Planets> result = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            List<PlanetService> planets = session.createQuery("FROM planetEntity").getResultList();
            planets.forEach((p) -> {
                result.add(Planets.valueOf(p.getId()));
            });
        }
        return result;
    }

    public List<PlanetService> getAllWithTickets() {
        List<PlanetService> result = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            result = session.createQuery("FROM planetEntity", PlanetService.class).getResultList();

            for (int i = 0; i < result.size(); i++) {
                Hibernate.initialize(result.get(i).getTicketsToPlanet());
                Hibernate.initialize(result.get(i).getTicketsFromPlanet());
            }
        }
        return result;
    }

    public boolean delete(Planets planet) {

        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(PlanetService.builder()
                    .id(planet.toString())
                    .name(planet.name)
                    .build()
            );
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
