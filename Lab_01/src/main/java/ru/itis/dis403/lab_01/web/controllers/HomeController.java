package ru.itis.dis403.lab_01.web.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.dis403.lab_01.di.annotation.Controller;
import ru.itis.dis403.lab_01.di.annotation.GetMapping;
import ru.itis.dis403.lab_01.web.Loader;

import java.io.IOException;

@Controller
public class HomeController {

    @GetMapping("/home")
    public void home(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String html = Loader.load("home.html");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(html);

    }
}
