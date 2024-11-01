package global.goit.edu.servlets;

import global.goit.edu.client.Client;
import global.goit.edu.client.ClientCrudService;
import global.goit.edu.database.DatabaseInitService;
import global.goit.edu.planet.PlanetCrudService;
import global.goit.edu.planet.PlanetService;
import global.goit.edu.planet.Planets;
import global.goit.edu.ticket.Ticket;
import global.goit.edu.ticket.TicketCrudService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(value = "")
public class IndexPageServlet extends HttpServlet {

    private String indexPagePath;
    private FileTemplateResolver resolver;
    private TemplateEngine engine;
    private ClientCrudService clientCrudService;
    private PlanetCrudService planetCrudService;
    private TicketCrudService ticketCrudService;

    @Override
    public void init() throws ServletException {
        resolver = new FileTemplateResolver();
        engine = new TemplateEngine();
        indexPagePath = getServletContext().getRealPath("/templates") + "\\";
        String dbPath = getServletContext().getRealPath("/db") + "\\" + "space_travel";
        clientCrudService = new ClientCrudService();
        planetCrudService = new PlanetCrudService();
        ticketCrudService = new TicketCrudService();

        DatabaseInitService.main(
                new String[] {dbPath}
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resolver.setPrefix(indexPagePath);
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);

        List<PlanetService> planets = new ArrayList<>();
        List<Client> clients = clientCrudService.getAllWithTickets();
        List<Ticket> tickets = ticketCrudService.getAllWithClients();

        for (Planets planet : planetCrudService.getAll()) {
            planets.add(PlanetService.builder()
                    .id(planet.toString())
                    .name(planet.name.toString())
                    .build()
            );
        }

        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("planets", planets)
        );

        simpleContext.setVariables(Map.of("clients", clients));
        simpleContext.setVariables(Map.of("tickets", tickets));

        engine.process("index", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
