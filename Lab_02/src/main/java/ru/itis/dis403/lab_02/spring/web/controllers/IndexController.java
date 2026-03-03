package ru.itis.dis403.lab_02.spring.web.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import ru.itis.dis403.lab_02.spring.di.annotation.Controller;
import ru.itis.dis403.lab_02.spring.di.annotation.GetMapping;
import ru.itis.dis403.lab_02.spring.web.Loader;

import java.io.IOException;

@Component
@Controller
public class IndexController {

    @GetMapping("/index")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String html = Loader.load("index.html");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(html);

    }
}
