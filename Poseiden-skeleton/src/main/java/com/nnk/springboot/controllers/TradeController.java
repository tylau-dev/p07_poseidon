package com.nnk.springboot.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.ITradeService;

@Controller
public class TradeController {
    private ITradeService tradeService;

    public TradeController(ITradeService tradeService) {
	this.tradeService = tradeService;
    }

    @RequestMapping("/trade/list")
    public String home(Model model) {
	List<Trade> tradeListResult = new ArrayList<Trade>();
	this.tradeService.getTrades().forEach(tradeListResult::add);

	model.addAttribute("tradeList", tradeListResult);
	return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
	return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "trade/add";
	}

	this.tradeService.saveTrade(trade);

	List<Trade> tradeListResult = new ArrayList<Trade>();
	this.tradeService.getTrades().forEach(tradeListResult::add);

	model.addAttribute("tradeList", tradeListResult);
	return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	Trade tradeResult = this.tradeService.getTradeById(id).get();

	model.addAttribute("trade", tradeResult);
	return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "trade/update";
	}

	this.tradeService.saveTrade(trade);

	List<Trade> tradeListResult = new ArrayList<Trade>();
	this.tradeService.getTrades().forEach(tradeListResult::add);

	model.addAttribute("tradeList", tradeListResult);
	return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
	this.tradeService.deleteTradeById(id);

	List<Trade> tradeListResult = new ArrayList<Trade>();
	this.tradeService.getTrades().forEach(tradeListResult::add);

	model.addAttribute("tradeList", tradeListResult);
	return "redirect:/trade/list";
    }
}
