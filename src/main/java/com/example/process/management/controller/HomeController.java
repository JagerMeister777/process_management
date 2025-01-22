package com.example.process.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

  @GetMapping("/home/{id}")
  public String view(@PathVariable ("id") Long id, Model model) {
    model.addAttribute("id", id);
    return "home";
  }
}
