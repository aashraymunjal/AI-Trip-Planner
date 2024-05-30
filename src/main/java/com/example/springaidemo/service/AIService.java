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
 				Give me in json format.use the city - {city} for a 1 day trip and each object should contain the following fields, places to visit or things to do as palces,distance from city center as distance, time needed to complete the activity as RequiredTime, pocket friendly good local place to have food with address in same line nearby that place as foodOptions.
					Make sure I dont miss the famous places. Give me the sorted list, so that it is convinient to travel from list of places that you give me. take care of directions.
					I dont need nested json structure. just give me objects in a single parent object-activities	""");
		promptTemplate.add("city", city);
		return this.aiClient.generate(promptTemplate.create()).getGeneration().getText();
	}

	// generate output in the JSON format via Prompting
	public String getChargePlan(String car, String range, String source, String destination) {
	    PromptTemplate promptTemplate = new PromptTemplate(
	            """
	            Using the car model {car} with a range of {range} km, provide a detailed charging plan for a trip from {source} to {destination} and back, finding chargers on the way. Only calculate the charging that i need to reach my destination.Assume that i am leaving at 0% charge. 
	            Return the result in JSON format with the following structure:
	            give me in 2 parent objects - going and returning.
	            Each of them should contain "name"- name of the charging station, "address" -address of the station, "pricePerKwh" - price to charge per kwh and "coffeeShop" nearby coffee shop of that charger.
	            
	            at end give me "totalCost" - as toital cost of charging on entire trip.
	            Make sure to include both going and returning trips and calculate the total cost
	            """);
	    promptTemplate.add("car", car);
	    promptTemplate.add("range", range);
	    promptTemplate.add("source", source);
	    promptTemplate.add("destination", destination);

	    AiResponse generate = this.aiClient.generate(promptTemplate.create());
	    return generate.getGeneration().getText();
	}

}
