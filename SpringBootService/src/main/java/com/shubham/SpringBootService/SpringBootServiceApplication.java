package com.shubham.SpringBootService;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.shubham.SpringBootService.controller.Library;
import com.shubham.SpringBootService.repository.LibraryRepository;


@SpringBootApplication    
public class SpringBootServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootServiceApplication.class, args);
	}
	
	/*@Override
	public void run(String[] args) {
		
			Library library= repository.findById("fasaf123").get();
			System.out.println(library.getAuthor());
			
			Library en = new Library();
			en.setAisle(1243);
			en.setAuthor("Ruman");
			en.setBook_name("Devops");
			en.setId("ha12223");
			en.setIsbn("231sqw");
			
			repository.save(en);
			
		List<Library> allrecords=repository.findAll();
			for(Library items:allrecords) {
				System.out.println(items.getAuthor());
			}
		
			
	}
	
	*/
}
