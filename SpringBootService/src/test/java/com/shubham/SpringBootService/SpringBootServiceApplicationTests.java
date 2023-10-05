package com.shubham.SpringBootService;


import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubham.SpringBootService.controller.AddResponse;
import com.shubham.SpringBootService.controller.Library;
import com.shubham.SpringBootService.controller.LibraryController;
import com.shubham.SpringBootService.repository.LibraryRepository;
import com.shubham.SpringBootService.service.LibraryService;



@SpringBootTest
@AutoConfigureMockMvc

class SpringBootServiceApplicationTests {

	@Autowired
	LibraryController con;
	
	@MockBean
	LibraryRepository repository;
	@MockBean 
	LibraryService libraryService;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	void contextLoads() {
		
		
	}
	
	@Test
	public void checkBiuldIDLogic() {
		LibraryService lib = new LibraryService();
		String id =lib.buildID("ZMAN",24);
		assertEquals(id,"OLDZMAN24");
	}
	@Test
	public void addBookTest() {
		//mock libraryservice ,repository 
		Library lib= buildLibrary();
	when(libraryService.buildID(lib.getIsbn(),lib.getAisle())).thenReturn(lib.getId());
	when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(false);
	when(repository.save(any())).thenReturn(lib);
	
	ResponseEntity response=con.addBookImplementation(buildLibrary());
	
	
	System.out.println(response.getStatusCode());
	assertEquals(response.getStatusCode(),HttpStatus.CREATED);
	
	   AddResponse ad = (AddResponse) response.getBody();
	   ad.getMsg();
	   ad.getId();
	   assertEquals(ad.getId(),lib.getId());
	   assertEquals(ad.getMsg(),"Wohhoooo !!!!! .....Success  book is added");
	   //call mock service from code
	   
	   
	   
	}
	//method for mock mvc 
	@Test
	public void addBookControllerLevelUnitTest() throws Exception {
		Library lib= buildLibrary();
		ObjectMapper map= new ObjectMapper();
		String jsonstring=map.writeValueAsString(lib);
		
		when(libraryService.buildID(lib.getIsbn(),lib.getAisle())).thenReturn(lib.getId());
		when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(true);	
		when(repository.save(any())).thenReturn(lib);
		this.mockMvc.perform(post("/addBook").contentType(MediaType.APPLICATION_JSON).content(jsonstring)).
		andExpect(status().isAccepted()).andDo(print()).andExpect(jsonPath("$.id").value(lib.getId()));
		
		
		
		
		
		
	}
	
	
	
	public Library buildLibrary() {
		Library lib = new Library();
		lib.setBook_name("Spring_Data_Tree");
		lib.setId("sfe123322567");
		lib.setAisle(322567);
		lib.setIsbn("sfe123");
		lib.setAuthor("Shubham_Pandey");
		
		return lib;
	}

}
