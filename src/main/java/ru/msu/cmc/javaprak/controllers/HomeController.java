package ru.msu.cmc.javaprak.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = { "/", "/index"})
    public String index() {
        return "index";
    }
//
//    @RequestMapping(value = "/allPersons" )
//    public String allPersons() {
//        return "persons";
//    }
//
//    @RequestMapping(value = "/generateTree")
//    public String generateTree() {
//        return "generateTree";
//    }
}