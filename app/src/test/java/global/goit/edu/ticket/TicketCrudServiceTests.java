package global.goit.edu.ticket;

import global.goit.edu.client.ClientCrudService;
import global.goit.edu.database.DatabaseForTestsInitService;
import global.goit.edu.hibernateservice.HibernateService;
import global.goit.edu.planet.PlanetService;
import global.goit.edu.planet.Planets;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TicketCrudServiceTests {

    private static TicketCrudService ticketCrudService;
    private static ClientCrudService clientCrudService;

    @BeforeAll
    public static void BeforeAll() {
        ticketCrudService = new TicketCrudService();
        clientCrudService = new ClientCrudService();
        DatabaseForTestsInitService.main(null);
    }

    @Test
    public void testThatMethodSaveWorkOk() {

        //Given
        Ticket expected = Ticket.builder()
                .createdAt("2024-10-31 16:10:01+00")
                .client(clientCrudService.findById(4))
                .planetServiceTo(PlanetService.builder()
                        .id(Planets.VEN.toString())
                        .name(Planets.VEN.name)
                        .build())
                .planetServiceFrom(PlanetService.builder()
                        .id(Planets.JUP.toString())
                        .name(Planets.JUP.name)
                        .build())
                .build();

        //When
        long expectedId = ticketCrudService.save(expected);
        expected.setId(expectedId);
        Ticket actual = new Ticket();

        try (Session session = HibernateService.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            List<Ticket> tickets = session.createQuery("FROM ticketEntity").getResultList();

            actual = tickets.get(tickets.size() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Then
        Assertions.assertEquals(expected.getId(), actual.getId());

        Assertions.assertEquals(
                expected.getClient().getId(),
                actual.getClient().getId()
        );

        Assertions.assertEquals(
                expected.getPlanetServiceTo().getName(),
                actual.getPlanetServiceTo().getName()
        );

        Assertions.assertEquals(
                expected.getPlanetServiceFrom().getName(),
                actual.getPlanetServiceFrom().getName()
        );
    }

    @Test
    public void testThatMethodSaveReturnMinusOneWhenClientIsNull() {
        //Given
        long expected = -1L;
        Ticket ticket = Ticket.builder()
                .createdAt("2024-10-31 16:10:01+00")
                .client(null)
                .planetServiceTo(PlanetService.builder()
                        .id(Planets.VEN.toString())
                        .name(Planets.VEN.name)
                        .build())
                .planetServiceFrom(PlanetService.builder()
                        .id(Planets.JUP.toString())
                        .name(Planets.JUP.name)
                        .build())
                .build();

        //When
        long actual = ticketCrudService.save(ticket);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testThatMethodSaveReturnMinusOneWhenPlanetServiceToIsNull() {
        //Given
        long expected = -1L;
        Ticket ticket = Ticket.builder()
                .createdAt("2024-10-31 16:10:01+00")
                .client(clientCrudService.findById(2))
                .planetServiceTo(null)
                .planetServiceFrom(PlanetService.builder()
                        .id(Planets.JUP.toString())
                        .name(Planets.JUP.name)
                        .build())
                .build();

        //When
        long actual = ticketCrudService.save(ticket);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testThatMethodSaveReturnMinusOneWhenPlanetServiceFromIsNull() {
        //Given
        long expected = -1L;
        Ticket ticket = Ticket.builder()
                .createdAt("2024-10-31 16:10:01+00")
                .client(clientCrudService.findById(2))
                .planetServiceTo(PlanetService.builder()
                        .id(Planets.VEN.toString())
                        .name(Planets.VEN.name)
                        .build())
                .planetServiceFrom(null)
                .build();

        //When
        long actual = ticketCrudService.save(ticket);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testThatMethodFindByClientIdWorkOk() {

        //Given
        List<Ticket> expected = clientCrudService.findByIdWithTickets(10).getTickets();

        //When
        List<Ticket> actual = ticketCrudService.findByClientId(clientCrudService.findById(10));

        //Then
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i).getId(), actual.get(i).getId());
        }
    }

    @Test
    public void testThatMethodFindByClientIdWithClientWorkOk() {

        //Given
        List<Ticket> expected = clientCrudService.findByIdWithTickets(10).getTickets();

        //When
        List<Ticket> actual = ticketCrudService.findByClientIdWithClient(clientCrudService.findById(10));

        //Then
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i).getId(), actual.get(i).getId());
            Assertions.assertEquals(
                    expected.get(i).getClient().getId(),
                    actual.get(i).getClient().getId()
                    );
        }
    }

    @Test
    public void testThatMethodGetMaxIdWorkOk() {

        //Given
        long expected = 1;
        try (Session session = HibernateService.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            List<Ticket> tickets = session.createQuery("FROM ticketEntity").getResultList();

            expected = tickets.get(
                            tickets.size() - 1
                    )
                    .getId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //When
        long actual = ticketCrudService.getMaxId();

        //Then
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void testThatMethodFindByIdWorkOk() {

        //Given
        Ticket expected = Ticket.builder()
                .id(2)
                .client(
                        clientCrudService.findById(2)
                )
                .createdAt("2024-09-13 05:01:01+00")
                .planetServiceFrom(PlanetService.builder()
                        .id(Planets.EAR.toString())
                        .name(Planets.EAR.name)
                        .build())
                .planetServiceTo(PlanetService.builder()
                        .id(Planets.MER.toString())
                        .name(Planets.MER.name)
                        .build())
                .build();

        //When
        Ticket actual = ticketCrudService.findById(2);

        //Then
        Assertions.assertEquals(expected.getId(), actual.getId());

        Assertions.assertEquals(
                expected.getPlanetServiceTo().getName(),
                actual.getPlanetServiceTo().getName()
        );

        Assertions.assertEquals(
                expected.getPlanetServiceFrom().getName(),
                actual.getPlanetServiceFrom().getName()
        );

    }

    @Test
    public void testThatMethodFindByIdWithClientWorkOk() {

        //Given
        Ticket expected = Ticket.builder()
                .id(2)
                .client(
                        clientCrudService.findById(2)
                )
                .createdAt("2024-09-13 05:01:01+00")
                .planetServiceFrom(PlanetService.builder()
                        .id(Planets.EAR.toString())
                        .name(Planets.EAR.name)
                        .build())
                .planetServiceTo(PlanetService.builder()
                        .id(Planets.MER.toString())
                        .name(Planets.MER.name)
                        .build())
                .build();

        //When
        Ticket actual = ticketCrudService.findByIdWithClient(2);

        //Then
        Assertions.assertEquals(expected.getId(), actual.getId());

        Assertions.assertEquals(
                expected.getClient().getId(),
                actual.getClient().getId()
        );

        Assertions.assertEquals(
                expected.getPlanetServiceTo().getName(),
                actual.getPlanetServiceTo().getName()
        );

        Assertions.assertEquals(
                expected.getPlanetServiceFrom().getName(),
                actual.getPlanetServiceFrom().getName()
        );

    }

    @Test
    public void testThatMethodUpdateWorkOk() {
        //Given
        Ticket expectedTicket = ticketCrudService.findByIdWithClient(4);
        expectedTicket.setCreatedAt("2024-11-25 05:01:01+00");
        expectedTicket.setPlanetServiceFrom(PlanetService.builder()
                        .id(Planets.JUP.toString())
                        .name(Planets.JUP.name)
                        .build()
        );

        //When
        boolean actual = ticketCrudService.update(expectedTicket);
        Ticket actualTicket = ticketCrudService.findByIdWithClient(4);


        //Then
        Assertions.assertTrue(actual);

        Assertions.assertEquals(expectedTicket.getId(), actualTicket.getId());

        Assertions.assertEquals(
                expectedTicket.getClient().getId(),
                actualTicket.getClient().getId()
        );

        Assertions.assertEquals(
                expectedTicket.getPlanetServiceTo().getName(),
                actualTicket.getPlanetServiceTo().getName()
        );

        Assertions.assertEquals(
                expectedTicket.getPlanetServiceFrom().getName(),
                actualTicket.getPlanetServiceFrom().getName()
        );
    }

    @Test
    public void testThatMethodDeleteWorkOk() {

        //Given
        Ticket ticket = ticketCrudService.findById(3L);

        //When
        boolean actual = ticketCrudService.delete(ticket);
        Ticket actualTicket = ticketCrudService.findById(3L);

        //Then
        Assertions.assertTrue(actual);
        Assertions.assertNull(actualTicket);

    }

}
