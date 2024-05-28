package com.example.springaidemo.controller;

import com.example.springaidemo.dto.bookDetails;
import com.example.springaidemo.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("askAI")
public class AIController {

	@Autowired
	AIService aiService;

	@GetMapping("/plan")
	public String getJoke(@RequestParam String city) {
		return aiService.planner(city);
	}

	@GetMapping("/charging")
	public String getChargePlan(@RequestParam String car, @RequestParam String range, @RequestParam String source,
			@RequestParam String destination) {
		return aiService.getChargePlan(car, range, source, destination);
	}

}
