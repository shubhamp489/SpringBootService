package com.shubham.SpringBootService.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

	@RestController
	public class GreetingController {
		@Autowired //create automatically object for your class
		Greeting greeting;

	AtomicLong counter=new AtomicLong();
	
	
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name")String name) {
		greeting.setContent("Hey I am Learning Spring Boot on Udemy from " + name );
		greeting.setId(counter.incrementAndGet());
		return greeting;
		
		
	}
	
	
	
	
	
	
}
