package com.bewerbungsbuddy.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String startSeite(Model model) {
        return "startseite";
    }

    @GetMapping("/unser-team")
    public String unserTeam(Model model) {
        return "unser_team";
    }

    @GetMapping("/pricing")
    public String pricing(Model model) {
        return "pricing";
    }

    // TODO: Add GET endpoints for login and registration pages
    // e.g. @GetMapping("/signup") and @GetMapping("/login") for form rendering in frontend
}
