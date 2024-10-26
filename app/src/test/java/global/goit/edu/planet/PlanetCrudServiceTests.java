package global.goit.edu.planet;

import global.goit.edu.database.DatabaseForTestsInitService;
import global.goit.edu.hibernateservice.HibernateService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class PlanetCrudServiceTests {

    private static PlanetCrudService planetCrudService;
    private static PlanetService planetService;

    @BeforeAll
    public static void BeforeAll() {
        planetCrudService = new PlanetCrudService();
        planetService = new PlanetService();
        DatabaseForTestsInitService.main(null);
    }

    @Test
    public void testThatMethodSaveWorkOk() {
        //Given
        Planets expected = Planets.PLUT;
        Planets actual;
        SessionFactory sessionFactory = HibernateService.getInstance().getSessionFactory();

        //When
        planetCrudService.getAll().forEach(System.out::println);
        boolean expectedResult = planetCrudService.save(expected);
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            PlanetService actualPlanetService = session.get(PlanetService.class, Planets.PLUT.toString());
            actual = planetService.getPlanet(actualPlanetService);
        }

        //Then
        Assertions.assertTrue(expectedResult);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testThatMethodGetAllWorkOk() {
        //Given
        List<Planets> expected = Arrays.asList(Planets.values());
        planetCrudService.save(Planets.PLUT);

        //When
        List<Planets> actual = planetCrudService.getAll();
        planetCrudService.delete(Planets.PLUT);

        //Then
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void testThatMethodDeleteWorkOk() {
        //Given
        List<Planets> expected = planetCrudService.getAll();
        planetCrudService.save(Planets.PLUT);

        //When
        boolean expectedResult = planetCrudService.delete(Planets.PLUT);
        List<Planets> actual = planetCrudService.getAll();

        //Then
        Assertions.assertTrue(expectedResult);
        Assertions.assertEquals(expected, actual);
    }
}
