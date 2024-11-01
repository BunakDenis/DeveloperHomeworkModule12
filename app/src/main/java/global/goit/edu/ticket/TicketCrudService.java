package global.goit.edu.ticket;

import global.goit.edu.client.Client;
import global.goit.edu.hibernateservice.HibernateService;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TicketCrudService {

    private SessionFactory sessionFactory;

    public TicketCrudService() {
        sessionFactory = HibernateService.getInstance().getSessionFactory();
    }

    public long save(Ticket ticket) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(ticket);
            transaction.commit();
            return getMaxId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1l;
    }

    public List<Ticket> findByClientId(Client client) {
        List<Ticket> result = new ArrayList<>();
        long clientId = client.getId();

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            return session
                    .createNativeQuery("SELECT * FROM ticket WHERE client_id = " + clientId, Ticket.class)
                    .getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Ticket> findByClientIdWithClient(Client client) {
        List<Ticket> result = new ArrayList<>();
        long clientId = client.getId();

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            result = session
                    .createNativeQuery("SELECT * FROM ticket WHERE client_id = " + clientId, Ticket.class)
                    .getResultList();
            result.forEach(
                    t -> Hibernate.initialize(t.getClient().getTickets())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public long getMaxId() {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            List<Ticket> tickets = session
                    .createQuery("FROM ticketEntity", Ticket.class)
                    .getResultList();

            return tickets.get(tickets.size() - 1).getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Long.parseLong(null);

    }

    public Ticket findById(long id) {
        Ticket result = new Ticket();

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            return session.get(Ticket.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Ticket findByIdWithClient(long id) {
        Ticket result = new Ticket();

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            result = session.get(Ticket.class, id);
            Hibernate.initialize(result.getClient().getTickets());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Ticket> getAll() {

        List<Ticket> result = new ArrayList<>();

        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            result = session.createQuery("FROM ticketEntity").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    public List<Ticket> getAllWithClients() {

        List<Ticket> result = new ArrayList<>();

        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            result = session.createQuery("FROM ticketEntity", Ticket.class).getResultList();
            result.forEach(
                    ticket -> Hibernate.initialize(ticket.getClient().getTickets())
            );
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean update(Ticket ticket) {

        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(ticket);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(Ticket ticket) {

        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(ticket);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}