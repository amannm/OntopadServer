package ontopad.web;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
@WebListener
public class OntopadContextListener implements ServletContextListener {

    private void registerServlet(ServletContext context, String name, String path) {
        ServletRegistration.Dynamic dynamic = context.addServlet(name, "ontopad.web." + name);
        dynamic.addMapping(path);
        dynamic.setLoadOnStartup(1);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {        
        ServletContext context = sce.getServletContext();
        context = sce.getServletContext();
        registerServlet(context, "RootServlet", "/");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
