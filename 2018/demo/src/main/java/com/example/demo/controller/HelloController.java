package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class HelloController {
    @RequestMapping("/index")
    public String index(Map<String,Object> map){
        map.put("name", "HelloController");
        return "index";
    }
}
