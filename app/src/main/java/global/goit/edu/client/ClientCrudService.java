package global.goit.edu.client;

import global.goit.edu.hibernateservice.HibernateService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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

        try (Session session = sessionFactory.openSession()) {
            return session.get(Client.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

/*Метод повертає останнього кліента, якщо в базі данних більше одного кліента з таким іменем.
  Якщо клієнта з таким іменем не має повернеться null
 */
    public Client findByName(String name) {
        Client result = null;

        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM clientEntity WHERE name =: name", Client.class);
            List<Client> clients = query.setParameter("name", name).list();

            for (Client client : clients) {
                if (client.getName().equals(name)){
                    result = client;
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Client> getAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM clientEntity", Client.class).getResultList();
        }
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
