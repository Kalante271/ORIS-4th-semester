package ru.itis.dis403.lab_01.di;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import ru.itis.dis403.lab_01.di.component.Application;
import ru.itis.dis403.lab_01.di.config.Context;
import ru.itis.dis403.lab_01.web.DispatcherServlet;

import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        Context context = new Context();

//        Application application = (Application) context.getComponent(Application.class);
//        application.run();

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
