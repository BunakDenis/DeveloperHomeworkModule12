package global.goit.edu.client;

import global.goit.edu.hibernateservice.HibernateService;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ClientCrudService {

    private SessionFactory sessionFactory;

    public ClientCrudService() {
        sessionFactory = HibernateService.getInstance().getSessionFactory();
    }

    public long save(Client client) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tr = session.beginTransaction();
            session.persist(client);
            tr.commit();
            return findById(
                    findByName(client.getName()).getId()
            ).getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Long.parseLong(null);
    }

    public Client findById(long id) {
        Client result = new Client();

        try(Session session = sessionFactory.openSession())  {
            result = session.get(Client.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Client findByIdWithTickets(long id) {
        Client result = new Client();

        try(Session session = sessionFactory.openSession())  {
            result = session.get(Client.class, id);
            Hibernate.initialize(result.getTickets());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Client findByName(String name) {
        Client result = new Client();

        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM clientEntity WHERE name =: name", Client.class);
            List<Client> clients = query.setParameter("name", name).list();

            for (Client client : clients) {
                if (client.getName().equals(name)){
                    result = client;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Client findByNameWithTickets(String name) {
        Client result = new Client();

        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM clientEntity WHERE name =: name", Client.class);
            List<Client> clients = query.setParameter("name", name).list();

            for (Client client : clients) {
                if (client.getName().equals(name)){
                    result = client;
                    Hibernate.initialize(result.getTickets());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Client> getAll() {
        List<Client> result = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            result = session.createQuery("FROM clientEntity", Client.class).getResultList();
        }
        return result;
    }

    public List<Client> getAllWithTickets() {
        List<Client> result = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            result = session.createQuery("FROM clientEntity", Client.class).getResultList();
            result.forEach(
                    client -> {
                        Hibernate.initialize(client.getTickets());
                        client.getTickets().forEach(ticket -> {
                            Hibernate.initialize(ticket.getPlanetServiceTo());
                            Hibernate.initialize(ticket.getPlanetServiceFrom());
                        });
                    }
            );
        }
        return result;
    }

    public boolean update(Client client) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tr = session.beginTransaction();
            session.merge(client);
            tr.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(Client client) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tr = session.beginTransaction();
            session.remove(client);
            tr.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
