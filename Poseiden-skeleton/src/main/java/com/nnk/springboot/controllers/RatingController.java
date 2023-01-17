package com.nnk.springboot.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.IRatingService;

@Controller
public class RatingController {

    private static final Logger logger = LogManager.getLogger("RatingController");

    private IRatingService ratingService;

    public RatingController(IRatingService ratingService) {
	this.ratingService = ratingService;
    }

    @RequestMapping("/rating/list")
    public String home(Model model) {
	logger.info("GET /rating/list");

	List<Rating> ratingListResult = new ArrayList<Rating>();
	this.ratingService.getRatings().forEach(ratingListResult::add);

	model.addAttribute("ratingList", ratingListResult);
	return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
	logger.info("GET /rating/add");

	return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
	logger.info("POST /rating/validate");

	if (result.hasErrors()) {
	    logger.error("Error with form input");
	    return "rating/add";
	}
	this.ratingService.saveRating(rating);

	return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	logger.info("GET /rating/update");

	Rating ratingResult = this.ratingService.getRatingById(id).get();

	model.addAttribute("rating", ratingResult);
	return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result,
	    Model model) {
	logger.info("POST /rating/update");

	if (result.hasErrors()) {
	    logger.error("Error with form input");
	    return "rating/update/{id}";
	}
	rating.setRatingId(id);

	this.ratingService.saveRating(rating);

	List<Rating> ratingListResult = new ArrayList<Rating>();
	this.ratingService.getRatings().forEach(ratingListResult::add);

	model.addAttribute("ratingList", ratingListResult);
	return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
	logger.info("GET /rating/delete");

	this.ratingService.deleteRatingById(id);

	List<Rating> ratingListResult = new ArrayList<Rating>();
	this.ratingService.getRatings().forEach(ratingListResult::add);

	model.addAttribute("ratingList", ratingListResult);

	return "redirect:/rating/list";
    }
}
