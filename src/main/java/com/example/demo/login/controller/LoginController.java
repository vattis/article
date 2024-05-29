package com.example.demo.login.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping()
@RequiredArgsConstructor
public class LoginController {
    @GetMapping("/login")
    public String login(){
        return "redirect:/articles";
    }
    @GetMapping("/logout")
    public String logout(){
        return "redirect:/articles";
    }

    @PostMapping("/test")
    @ResponseBody
    public String test(){
        return "<h1>test 통과</h1>";
    }
}
