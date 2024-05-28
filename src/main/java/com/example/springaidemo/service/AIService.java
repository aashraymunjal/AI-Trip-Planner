package com.example.springaidemo.service;

import com.example.springaidemo.dto.bookDetails;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.AiResponse;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.parser.ListOutputParser;
import org.springframework.ai.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIService {

	@Autowired
	AiClient aiClient;

	public String planner(String city) {
		PromptTemplate promptTemplate = new PromptTemplate(
				"""
						 Please plan a one day trip to the {city}. Make sure I cover all the important places to see around.
						 Also give me some suggestions for restaurants. I wont be staying any where in the city. I am travelling with my family. Also include time to reach places.
						 Give me in readable format. The content should be to the point. Also include total drive of my iternary in km.
						 Give me answer in seperate lines with full stops only at end. Start with new line always
						""");
		promptTemplate.add("city", city);
		return this.aiClient.generate(promptTemplate.create()).getGeneration().getText();
	}

	// generate output in the JSON format via Prompting
	public String getChargePlan(String car, String range, String source, String Destination) {
		PromptTemplate promptTemplate = new PromptTemplate(
				"""
						Please provide iternary details about charging stations which i will need for the mentioned {car}
						with the given {range} starting from central part of {source} till {destination} (both to and fro).
						Also give me an estimate of cost of my charge at each charging station.
						Give me total cost of trip at last.
						Dont number my steps. Just break line when you finish with one sentence
						Give me answer in seperate lines with full stops only at end. Start with new line always.
						Dont use any special characters like - or :.
						 
						""");
		promptTemplate.add("car", car);
		promptTemplate.add("range", range);
		promptTemplate.add("source", source);
		promptTemplate.add("destination", Destination);

		AiResponse generate = this.aiClient.generate(promptTemplate.create());
		return generate.getGeneration().getText();
	}

}
