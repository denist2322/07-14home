package com.mysite.sbb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping("test")
    @ResponseBody
    public String showTest(){
        return "안녕하세";
    }

    @RequestMapping("/")
    public String showMain(){
        return "redirect:/question/list";
    }
}
