package com.nnk.springboot.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * Default Controller used after logging
 */
@Controller
public class DefaultController {
    @RequestMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {
	boolean hasUserRole = authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN"));
	if (hasUserRole) {
	    return "redirect:/user/list";
	}
	return "redirect:/bidList/list";
    }

}