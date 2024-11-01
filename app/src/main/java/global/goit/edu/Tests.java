package global.goit.edu;

import global.goit.edu.client.Client;
import global.goit.edu.client.ClientCrudService;
import global.goit.edu.ticket.Ticket;
import global.goit.edu.ticket.TicketCrudService;

import java.io.File;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class Tests {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //DatabaseInitService.main(null);

/*        String[] tpcMode = new String[]{"-tcpAllowOthers"};
        Server server = Server.createTcpServer(tpcMode);
        server.start();
        ClientCrudService clientCrudService = new ClientCrudService();
        clientCrudService.getAll().forEach(System.out::println);*/

        TicketCrudService ticketCrudService = new TicketCrudService();

/*        Ticket ticket = Ticket.builder()
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

        ticketCrudService.save(ticket);*/

/*        List<Ticket> all = ticketCrudService.findByClientIdWithClient(new ClientCrudService().findById(2L));

        Client client = all.get(0).getClient();

        System.out.println("client = " + client);*/

        ClientCrudService clientCrudService = new ClientCrudService();
        clientCrudService.delete(clientCrudService.findById(11));
        clientCrudService.delete(clientCrudService.findById(12));
        clientCrudService.delete(clientCrudService.findById(13));

    }
}