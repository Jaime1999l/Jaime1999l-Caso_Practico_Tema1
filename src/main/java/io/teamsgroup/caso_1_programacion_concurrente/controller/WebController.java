package io.teamsgroup.caso_1_programacion_concurrente.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value = {"/", "/user/**", "/admin/**", "/login", "/dashboard"})
    public String index() {
        return "index"; // El nombre del archivo HTML en src/main/resources/templates/index.html
    }
}

