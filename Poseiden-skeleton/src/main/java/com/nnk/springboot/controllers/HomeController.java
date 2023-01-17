package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    private static final Logger logger = LogManager.getLogger("HomeController");

    @RequestMapping("/")
    public String home(Model model) {
	logger.info("GET /");

	return "home";
    }

    @RequestMapping("/admin/home")
    public String adminHome(Model model) {
	return "redirect:/bidList/list";
    }

}
