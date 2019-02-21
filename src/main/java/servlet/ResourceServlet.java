package servlet;

import main.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resources.TestResource;
import sax.ReadXMLFileSAX;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourceServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(Main.class.getName());

     TestResource resource;

    public ResourceServlet(TestResource resource) {
        this.resource = resource;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("<H1>Hello!</H1><br>");
        response.getWriter().println("<p>Jetty based WebApp!</p><br>");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String path = request.getParameter("path");
        TestResource tmpRes = (TestResource) ReadXMLFileSAX.readXML(path);
        resource.setAge(tmpRes.getAge());
        resource.setName(tmpRes.getName());

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("Path: " + path);
        System.out.println("name = " + resource.getName());
        System.out.println("age = " + resource.getAge());

        response.setStatus(HttpServletResponse.SC_OK);
    }
}

