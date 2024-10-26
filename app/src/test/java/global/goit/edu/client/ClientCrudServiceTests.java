package global.goit.edu.client;

import global.goit.edu.database.DatabaseForTestsInitService;
import global.goit.edu.hibernateservice.HibernateService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ClientCrudServiceTests {
    private static ClientCrudService clientCrudService;

    @BeforeAll
    public static void BeforeAll() {
        clientCrudService = new ClientCrudService();
        DatabaseForTestsInitService.main(null);
    }

    @Test
    public void testThatMethodSaveWorkOk() {

        //Given
        Client expected = Client.builder()
                .name("Elon Mask")
                .build();
        List<Client> actual;
        SessionFactory sessionFactory = HibernateService.getInstance().getSessionFactory();

        //When
        expected.setId(
                clientCrudService.save(expected)
        );

        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM clientEntity WHERE name =: name");
            query.setParameter("name", expected.getName());
            actual = query.list();
        }

        //Then
        Assertions.assertEquals(expected.getId(), actual.get(0).getId());
        Assertions.assertEquals(expected.getName(), actual.get(0).getName());
    }

    @Test
    public void testThatMethodFindByIdWorkOk() {

        //Given
        Client expected = Client.builder()
                .name("Lady Gaga")
                .build();

        expected.setId(
                clientCrudService.save(expected)
        );

        //When
        Client actual = clientCrudService.findById(expected.getId());

        //Then
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void testThatMethodFindByNameWorkOk() {

        //Given
        Client expected = Client.builder()
                .name("Vin Diesel")
                .build();

        expected.setId(
                clientCrudService.save(expected)
        );

        //When
        Client actual = clientCrudService.findByName(expected.getName());

        //Then
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());

    }

    @Test
    public void testThatMethodUpdateWorkOk() {

        //Given
        Client expected = Client.builder()
                .name("John Doe")
                .build();

        expected.setId(
                clientCrudService.save(expected)
        );
        expected.setName("Mikola Gum");

        //When
        boolean expectedResultOfUpdateClient = clientCrudService.update(expected);
        Client actual = clientCrudService.findById(expected.getId());

        //Then
        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(expectedResultOfUpdateClient);

    }

    @Test
    public void testThatMethodDeleteWorkOk() {

        //Given
        List<Client> actual = new ArrayList<>();
        SessionFactory sessionFactory = HibernateService.getInstance().getSessionFactory();
        Client expected = Client.builder()
                .name("Frank Kafka")
                .build();
        expected.setId(
                clientCrudService.save(expected)
        );

        //When
        boolean expectedResultOfDeleteClient = clientCrudService.delete(expected);

        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Client> query = session.createQuery("FROM clientEntity", Client.class);
            actual = query.list();
            transaction.commit();
        }

        //Then
        Assertions.assertFalse(actual.contains(expected));
        Assertions.assertTrue(expectedResultOfDeleteClient);

    }

}

