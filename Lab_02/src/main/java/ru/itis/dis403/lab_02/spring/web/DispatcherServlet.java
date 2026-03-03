package ru.itis.dis403.lab_02.spring.web;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import ru.itis.dis403.lab_02.spring.di.annotation.Controller;
import ru.itis.dis403.lab_02.spring.di.annotation.GetMapping;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private final ApplicationContext context;
    private final Map<String, Method> methodsMapping = new HashMap<>();

    public DispatcherServlet(ApplicationContext context) {
        this.context = context;
        initMappings();
    }

    private void initMappings() {
        String[] beanNames = context.getBeanDefinitionNames();

        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);
            Class<?> clazz = bean.getClass();

            if (!clazz.isAnnotationPresent(Controller.class)) {
                continue;
            }

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(GetMapping.class)) {
                    String path = method.getAnnotation(GetMapping.class).value();
                    methodsMapping.put(path, method);
                }
            }
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getRequestURI();

        Method method = methodsMapping.get(path);

        if (method == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("404 Not Found");
            return;
        }

        try {
            Object controller = context.getBean(method.getDeclaringClass());
            method.invoke(controller, request, response);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("500 Server Error");
        }

    }
}
