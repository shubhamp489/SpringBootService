package com.shubham.SpringBootService.repository;

import java.util.List;

import com.shubham.SpringBootService.controller.Library;

public interface LibraryRepositoryCustom {
	List<Library> findAllByAuthor(String authorName);
}
