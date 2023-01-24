package com.bknote71.ajouportal.controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.PanelUI;

@RestController
public class HomeController {

    @GetMapping
    public String hello() {
        return "this is home page";
    }
}
