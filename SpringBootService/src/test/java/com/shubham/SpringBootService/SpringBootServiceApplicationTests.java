package com.shubham.SpringBootService;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
	

	
	@Test
	public void getsBookByAuthorName() throws Exception{
		
		List<Library> li = new ArrayList<Library>();
		li.add(buildLibrary());
		li.add(buildLibrary());
		
		when(repository.findAllByAuthor(any())).thenReturn(li);
		//mock call
		this.mockMvc.perform(get("/getBooks/author").
				param("authorname", "Shubham_Pandey")).andDo(print()).
		andExpect(status().isOk()).andExpect(jsonPath("$.length()",is(2))).
		andExpect(jsonPath("$.[0].id").value("sfe123322567"));
	}
	
	
	
	@Test
	public void updateBookTest() throws Exception {
		Library lib = buildLibrary();
		ObjectMapper map=new ObjectMapper();
		String jsonString=map.writeValueAsString(UpdateLibrary());
		
		when(libraryService.getBookById(any())).thenReturn(buildLibrary());
		
		this.mockMvc.perform(put("/updateBook/"+lib.getId()).contentType(MediaType.APPLICATION_JSON).content(jsonString)).
		andDo(print()).andExpect(status().isOk()).andExpect(content().
				json("{\"book_name\":\"Spring_Data_Tree\",\"id\":\"sfe123322567\",\"isbn\":\"sfe123\",\"aisle\":1233456,\"author\":\"Shubham_Pandey\"}"));
		
		
	}
	
	@Test
	public void deleteBookTest() throws Exception {
		when(libraryService.getBookById(any())).thenReturn(buildLibrary());
		doNothing().when(repository).delete(buildLibrary());
		this.mockMvc.perform(delete("/deleteBook").
				contentType(MediaType.APPLICATION_JSON).content("{\"id\":\"sfe123\"}")).andDo(print())
		.andExpectAll(status().isCreated()).andExpect(content().string("Congratulationss !!!!! Book is deleted.."));
		
		
		
	}
	
	
	
	
	public Library UpdateLibrary() {
		Library lib = new Library();
		lib.setBook_name("Spring_Data_Tree");
		lib.setId("sfe12341233456");
		lib.setAisle(1233456);
		lib.setIsbn("sfe1234");
		lib.setAuthor("Shubham_Pandey");
		
		return lib;
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
