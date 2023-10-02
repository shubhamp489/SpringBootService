package com.shubham.SpringBootService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.shubham.SpringBootService.controller.AddResponse;
import com.shubham.SpringBootService.controller.Library;
import com.shubham.SpringBootService.controller.LibraryController;
import com.shubham.SpringBootService.repository.LibraryRepository;
import com.shubham.SpringBootService.service.LibraryService;



@SpringBootTest
class SpringBootServiceApplicationTests {

	@Autowired
	LibraryController con;
	
	@MockBean
	LibraryRepository repository;
	@MockBean 
	LibraryService libraryService;
	
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
	when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(true);	
	ResponseEntity response=con.addBookImplementation(buildLibrary());
	
	
	System.out.println(response.getStatusCode());
	assertEquals(response.getStatusCode(),HttpStatus.ACCEPTED);
	
	   AddResponse ad = (AddResponse) response.getBody();
	   ad.getMsg();
	   ad.getId();
	   assertEquals(ad.getId(),lib.getId());
	   assertEquals(ad.getMsg(),"Hmmm !!!! Book Already Exist !");
	   
	}
	
	public Library buildLibrary() {
		Library lib = new Library();
		lib.setBook_name("Spring");
		lib.setId("sfe322");
		lib.setAisle(322);
		lib.setIsbn("sfe");
		lib.setAuthor("Shubham Pandey");
		
		return lib;
	}

}
