package com.wcx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @GetMapping("/FromZerotoExpert")
    public String Test01() {
        return "Hi, Welcome To From Zero To Expert";
    }
}
