package ru.itis.dis403.lab_05.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.dis403.lab_05.security.UserDetailsImpl;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        model.addAttribute("user", userDetails.getUsername());
        return "index";
    }
}