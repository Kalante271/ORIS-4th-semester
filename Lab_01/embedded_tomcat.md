# Встроенный Tomcat

```xml 
    <dependencies>
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-core</artifactId>
            <version>10.1.18</version>
        </dependency>
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-jasper</artifactId>
            <version>10.1.18</version>
        </dependency>
    </dependencies>
```

```java
Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        Connector conn = new Connector();
        conn.setPort(8090);
        tomcat.setConnector(conn);

        String contextPath = "";

        String docBase = new File(".").getAbsolutePath();
        Context tomcatContext = tomcat.addContext(contextPath, docBase);

        /*
            Создаем сервлет, лучше разместить этот код в отдельном файле
         */
/*
        HttpServlet servlet = new HttpServlet() {
            @Override
            protected void service(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException {
                resp.setContentType("text/html; charset=utf-8");
                PrintWriter writer = resp.getWriter();
                writer.println("<html><head><meta charset='utf-8'/><title>Embeded Tomcat</title></head><body>");
                writer.println("<h1>Мы встроили Tomcat в свое приложение!</h1>");

                writer.println("<div>Метод: " + req.getMethod() + "</div>");
                writer.println("<div>Ресурс: " + req.getPathInfo() + "</div>");
                writer.println("</body></html>");
            }
        };
*/

        /*
            Динамически подключаем севлет
         */
        String servletName = "dispatcherServlet"; // любое уникальное имя
        tomcat.addServlet(contextPath, servletName, new DispatcherServlet());
        // Указываем имя ресурса и сервлет, который этот ресурс будет обрабатывать
        // (по пути "/*" наш сервлет будет перехватывать все запросы)
        tomcatContext.addServletMappingDecoded("/*", servletName);

        try {
            tomcat.start();
            tomcat.getServer().await();

            /*
                tomcat.stop()
                tomcat.destroy()
             */
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
```