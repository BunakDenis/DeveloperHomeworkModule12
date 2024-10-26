package global.goit.edu.planet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PlanetServiceTests {

    private static PlanetService planetService;

    @BeforeAll
    public static void BeforeAll() {
        planetService = new PlanetService();
    }

    @Test
    public void testThatPlanetServiceConstructorThrowExceptionForInvalidInputValue() {

        //Given
        String id = "JUPI";
        String expectedExceptionMessage = "Invalid planet id = " + id;

        //Then
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    new PlanetService(id, Planets.JUP.name());
                }
        );
        Assertions.assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    public void testThatPlanetServiceConstructorThrowExceptionForInvalidInputName() {

        //Given
        String name = "Jupiti";
        String expectedExceptionMessage = "Invalid planet name = " + name;

        //Then
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    new PlanetService(Planets.JUP.toString(), name);
                }
        );
        Assertions.assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    public void testThatMethodSetIdThrowExceptionForInvalidInputId() {

        //Given
        String id = "MAR";
        String expectedExceptionMessage = "Invalid planet id = " + id;

        //Then
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    new PlanetService().setId(id);
                }
        );
        Assertions.assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    public void testThatMethodSetIdThrowExceptionForInvalidInputName() {

        //Given
        String name = "Marcus";
        String expectedExceptionMessage = "Invalid planet name = " + name;

        //Then
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    new PlanetService().setName(name);
                }
        );
        Assertions.assertEquals(expectedExceptionMessage, exception.getMessage());
    }

}
