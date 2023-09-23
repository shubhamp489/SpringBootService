package com.shubham.SpringBootService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shubham.SpringBootService.controller.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library,String> ,LibraryRepositoryCustom{
			
}
