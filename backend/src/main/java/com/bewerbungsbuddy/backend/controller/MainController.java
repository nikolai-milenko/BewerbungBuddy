package com.bewerbungsbuddy.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String startSeite() {
        return "forward:/index.html";
    }

    @GetMapping("/our-team")
    public String unserTeam() {
        return "forward:/our-team.html";
    }

    @GetMapping("/pricing")
    public String pricing() {
        return "forward:/pricing.html";
    }

    @GetMapping("/cv-analysis")
    public String cvAnalysis() {
        return "forward:/cv-analysis.html";
    }

    @GetMapping("/letter-generation")
    public String letterGeneration() {
        return "forward:/letter-generation.html";
    }
    // TODO: Add GET endpoints for login and registration pages
    // e.g. @GetMapping("/signup") and @GetMapping("/login") for form rendering in frontend
}
