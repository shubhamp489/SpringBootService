package com.shubham.SpringBootService.controller;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	private static final Logger logger=	LoggerFactory.getLogger(LibraryController.class);  // this is for logging purpose 
	
	
	@PostMapping("/addBook")
	public ResponseEntity addBookImplementation(@RequestBody Library library) 
	{	
		String id = libraryService.buildID(library.getIsbn(),library.getAisle());
		AddResponse ad = new AddResponse();
		if(!libraryService.checkBookAlreadyExist(id)) {
		logger.info("Book do not exist so creating one");
		
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
	
	
	@PutMapping("/updateBook/{id}")
	public ResponseEntity<Library> updateBook(@PathVariable(value="id")String id ,@RequestBody Library library) {
		//Library existingbook=repository.findById(id).get();//mock
		Library existingbook=libraryService.getBookById(id);
		
		existingbook.setAisle(library.getAisle());
		existingbook.setAuthor(library.getAuthor());
		existingbook.setBook_name(library.getBook_name());
		repository.save(existingbook);
		//spring boot will converts java object into json and publish value
		return new ResponseEntity<Library>(existingbook,HttpStatus.OK);
		
	}
	@DeleteMapping("/deleteBook")
	public ResponseEntity<String> deleteBookByid(@RequestBody Library library) {
	//	Library libdelete=repository.findById(library.getId()).get();
		Library libdelete=libraryService.getBookById(library.getId());
		repository.delete(libdelete);
		logger.info("Book is deleted !!!!");
		return new ResponseEntity<>("Congratulationss !!!!! Book is deleted..",HttpStatus.CREATED);
		
	}
	@GetMapping("getAllBooks")
	public List<Library> getAllbooksInDB(Library library) {
	List<Library>	allbooks=repository.findAll();
	return allbooks;	
		
	
	}
}
