package buslaev;

import buslaev.build.Autowired;
import buslaev.controller.Controller;
import buslaev.build.Context;
import buslaev.model.ModelSQLDAO;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    @Autowired
    private static Controller controller;

    @Autowired
    private static ModelSQLDAO dao = new ModelSQLDAO();

    static {
        try {
            Context.injectDependencies();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main (String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath("/api");
        servletContextHandler.addServlet(new ServletHolder(controller), "/*");
        server.setHandler(servletContextHandler);
        server.start();
        server.join();
    }
}
