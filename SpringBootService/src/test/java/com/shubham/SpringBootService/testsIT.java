package com.shubham.SpringBootService;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import com.shubham.SpringBootService.controller.Library;

@SpringBootTest
public class testsIT {
	@Test
	public void getAuthorNameBooksTest() throws Exception {
		
		
		String expected= "[\r\n"
				+ "    {\r\n"
				+ "        \"book_name\": \"Data_Bin\",\r\n"
				+ "        \"id\": \"fsfs2323\",\r\n"
				+ "        \"isbn\": \"fsfs23\",\r\n"
				+ "        \"aisle\": 23,\r\n"
				+ "        \"author\": \"Vicky\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"book_name\": \"Automation\",\r\n"
				+ "        \"id\": \"fsfs2312\",\r\n"
				+ "        \"isbn\": \"fsfs231\",\r\n"
				+ "        \"aisle\": 2,\r\n"
				+ "        \"author\": \"Vicky\"\r\n"
				+ "    }\r\n"
				+ "]";
		
		
		
		
		TestRestTemplate restTemplate= new TestRestTemplate();
		ResponseEntity<String> response= restTemplate.getForEntity("http://localhost:9090/getBooks/author?authorname=Vicky",String.class);
		 System.out.println(response.getStatusCode());
		 System.out.println(response.getBody());
		 JSONAssert.assertEquals(expected,response.getBody(),false);
	}
	
	
	@Test
	public void addBookIntegrationTest() {
		TestRestTemplate restTemplate= new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Library> request = new HttpEntity<Library>(buildLibrary(),headers);
		
		ResponseEntity<String> response= restTemplate.postForEntity("http://localhost:9090/addBook",request,String.class);
		assertEquals(HttpStatus.CREATED,response.getStatusCode());
		
		  System.out.println(response.getHeaders().get("unique").get(0));

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
