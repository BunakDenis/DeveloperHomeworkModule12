package global.goit.edu.ticket;

import global.goit.edu.client.Client;
import global.goit.edu.planet.PlanetService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ticketEntity")
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created_at")
    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "from_planet_id", nullable = false)
    private PlanetService planetServiceFrom;

    @ManyToOne
    @JoinColumn(name = "to_planet_id", nullable = false)
    private PlanetService planetServiceTo;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", createdAt='" + createdAt + '\'' +
                ", fromPlanet=" + planetServiceFrom.getName() +
                ", toPlanet=" + planetServiceTo.getName() +
                '}';
    }
}
