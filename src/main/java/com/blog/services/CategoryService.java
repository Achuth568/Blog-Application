package com.blog.services;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface CategoryService {
	//create 
	CategoryDto createCategoryService(CategoryDto categoryDto);
	
	//update
	CategoryDto updateCategoryService(CategoryDto categoryDto,long id);
	
	
	//delete
	
	void deleteCategoryService(long id);
	
	//get
	CategoryDto getCategoryService(long id);
	
	
	
	//getAll
	List<CategoryDto> getAllCategoryService();
	

}
