package com.shubham.SpringBootService.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.shubham.SpringBootService.repository.LibraryRepository;
import com.shubham.SpringBootService.service.LibraryService;

@RestController 
public class LibraryController {
	@Autowired
	LibraryRepository repository;
	
	@Autowired
	LibraryService libraryService;
	
	
	@PostMapping("/addBook")
	public ResponseEntity addBookImplementation(@RequestBody Library library) 
	{	
		String id = libraryService.buildID(library.getIsbn(),library.getAisle());
		AddResponse ad = new AddResponse();
		if(!libraryService.checkBookAlreadyExist(id)) {
		
		
		library.setId(library.getIsbn()+library.getAisle());
		repository.save(library);
		
		//to create headers
		HttpHeaders headers = new HttpHeaders();
		headers.add("unique", id);
		
		
		ad.setMsg("Wohhoooo !!!!! .....Success  book is added");
		ad.setId(library.getIsbn()+library.getAisle());
		
		//Response entity will convert java object into json response 
		//return ad;
		return new ResponseEntity<AddResponse>(ad,headers,HttpStatus.CREATED);
		}
		else {
			
			ad.setMsg("Hmmm !!!! Book Already Exist !");
			ad.setId(id);
			return new ResponseEntity<AddResponse>(ad,HttpStatus.ACCEPTED);
			
		}
		
		
		}
	
	@GetMapping("/getBooks/{id}")
		public Library getBookByID(@PathVariable(value="id")String id) {
		try {
		Library lib=	repository.findById(id).get();
		return lib;}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
				}
	
	@GetMapping("getBooks/author")
	public List<Library> getBooksbyAuthorName(@RequestParam(value="authorname")String authorname) {
		return repository.findAllByAuthor(authorname);
	}
	
	
}
