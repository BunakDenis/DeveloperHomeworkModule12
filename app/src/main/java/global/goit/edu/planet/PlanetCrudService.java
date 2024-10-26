package global.goit.edu.planet;

import global.goit.edu.hibernateservice.HibernateService;
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
            Query query = session.createQuery("FROM planetEntity");
            List<PlanetService> planets = query.list();
            planets.forEach((p) -> {
                result.add(Planets.valueOf(p.getId()));
            });
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
