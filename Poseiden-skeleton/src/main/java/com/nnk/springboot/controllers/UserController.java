package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.IUserService;

@Controller
public class UserController {
    private static final Logger logger = LogManager.getLogger("UserController");

    @Autowired
    private IUserService userService;

    @RequestMapping("/user/list")
    public String home(Model model) {
	logger.info("GET /user/list");

	model.addAttribute("users", userService.getUsers());
	return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User bid) {
	logger.info("GET /user/add");

	return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
	logger.info("POST /user/validate");

	if (!result.hasErrors()) {
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    user.setPassword(encoder.encode(user.getPassword()));
	    userService.saveUser(user);
	    model.addAttribute("users", userService.getUsers());
	    return "redirect:/user/list";
	}

	logger.error("Error with form input");

	return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	logger.info("GET /user/update");

	User user = userService.getUserById(id)
		.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	user.setPassword("");
	model.addAttribute("user", user);
	return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
	logger.info("POST /user/update");

	if (result.hasErrors()) {
	    logger.error("Error with form input");
	    return "user/update";
	}

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	user.setPassword(encoder.encode(user.getPassword()));
	user.setId(id);
	userService.saveUser(user);
	model.addAttribute("users", userService.getUsers());
	return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
	logger.info("GET /user/delete");

	User user = userService.getUserById(id)
		.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	userService.deleteUserById(id);
	model.addAttribute("users", userService.getUsers());
	return "redirect:/user/list";
    }
}
