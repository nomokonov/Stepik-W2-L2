package main;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import resource.TestResource;
import servlet.ResourceServlet;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;


public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception  {
        int sport = 8080;
        if (args.length == 1) {
            sport = Integer.valueOf(args[0]);
        }

        TestResource resource = new TestResource();

        MBeanServer mbServer = ManagementFactory.getPlatformMBeanServer();
        mbServer.registerMBean(resource, new ObjectName("Admin:type=ResourceServerController"));


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new ResourceServlet(resource)), "/*");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{ context});

        Server server = new Server(sport);
        server.setHandler(handlers);

        server.start();
        System.out.println("Server started");
        server.join();
    }



}
