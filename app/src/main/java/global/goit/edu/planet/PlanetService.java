package global.goit.edu.planet;

import global.goit.edu.ticket.Ticket;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "planetEntity")
@Table(name = "planet")
public class PlanetService {

    @Getter
    @Id
    private String id;

    @Getter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @OneToMany(mappedBy = "planetServiceFrom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> ticketsFromPlanet;

    @Getter
    @Setter
    @OneToMany(mappedBy = "planetServiceTo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> ticketsToPlanet;

    public PlanetService(String id, String name) {
        if (checkForValidPlanetValue(id)) {
            this.id = id;
        }
        if (checkForValidPlanetName(name)) {
            this.name = name;
        }
    }

    public void setId(String id) {
        if (checkForValidPlanetValue(id)) {
            this.id = id;
        }
    }

    public void setName(String name) {
        if (checkForValidPlanetName(name)) {
            this.name = name;
        }
    }

    public boolean checkForValidPlanetName(String name) {

        Planets[] planets = Planets.values();

        for (Planets planet : planets) {
            if (planet.name.equals(name)) {
                return true;
            }
        }
        throw new IllegalArgumentException("Invalid planet name = " + name);
    }

    public boolean checkForValidPlanetValue(String value) {

        Planets[] planets = Planets.values();

        for (Planets planet : planets) {
            if (planet.toString().equals(value)) {
                return true;
            }
        }
        throw new IllegalArgumentException("Invalid planet id = " + value);
    }

    public Planets getPlanet(PlanetService planetService) {
        Planets[] planets = Planets.values();

        for (Planets planet : planets) {
            if (planetService.getId().equals(planet.toString())) {
                return planet;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "PlanetService{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", count tickets to planet= " + ticketsToPlanet.size() +
                ", count tickets from planet= " + ticketsFromPlanet.size() +
                '}';
    }
}
