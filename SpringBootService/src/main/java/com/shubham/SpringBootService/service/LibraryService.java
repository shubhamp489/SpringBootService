package com.shubham.SpringBootService.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shubham.SpringBootService.controller.Library;
import com.shubham.SpringBootService.repository.LibraryRepository;

@Service
public class LibraryService {
	@Autowired		
	LibraryRepository repository;
	
	public String buildID(String isbn,int aisle) {
		return isbn+aisle;
	}
	
	
	public Boolean checkBookAlreadyExist(String id) {
	Optional<Library>lib =	repository.findById(id);
	if(lib.isPresent())
		return true;
	else
	return false;
		
	
						
			}
}
