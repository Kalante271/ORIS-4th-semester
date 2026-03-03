package ru.itis.dis403.lab_02.spring;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.dis403.lab_02.spring.di.config.Config;
import ru.itis.dis403.lab_02.spring.di.component.Application;
import ru.itis.dis403.lab_02.spring.web.DispatcherServlet;

import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException {

        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Application app = context.getBean(Application.class);
        app.run();



        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");

        Connector conn = new Connector();
        conn.setPort(8090);
        tomcat.setConnector(conn);

        String contextPath = "";

        String docBase = new File(".").getAbsolutePath();
        org.apache.catalina.Context tomcatContext = tomcat.addContext(contextPath, docBase);

        DispatcherServlet dispatcher = new DispatcherServlet(context);

        tomcat.addServlet(contextPath, "dispatcher", dispatcher);
        tomcatContext.addServletMappingDecoded("/*", "dispatcher");

        tomcat.start();
        tomcat.getServer().await();
    }
}
