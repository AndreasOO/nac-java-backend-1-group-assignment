package org.josandlin.javabackend1group.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class Hello {

    @GetMapping("/")
    public String hello() {
        return "welcome";
    }
}
