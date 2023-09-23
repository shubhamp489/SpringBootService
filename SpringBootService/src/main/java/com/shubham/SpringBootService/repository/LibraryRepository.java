package com.shubham.SpringBootService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shubham.SpringBootService.controller.Library;

public interface LibraryRepository extends JpaRepository<Library,String> {
			
}
