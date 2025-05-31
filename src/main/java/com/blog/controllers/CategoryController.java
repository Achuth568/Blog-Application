package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.impl.CategoryServiceImpl;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	
	
	@Autowired
	private CategoryServiceImpl categoryServiceImpl;
	
	@PostMapping("/save")
	public ResponseEntity<CategoryDto> saveController(@Valid @RequestBody CategoryDto categoryDto){
		
		return new  ResponseEntity<CategoryDto>(categoryServiceImpl.createCategoryService(categoryDto),HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<CategoryDto> updateController(@Valid @RequestBody CategoryDto categoryDto,@PathVariable long id){
		return ResponseEntity.ok(categoryServiceImpl.updateCategoryService(categoryDto, id));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteController(@PathVariable long id) {
		
		categoryServiceImpl.deleteCategoryService(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted sucessfully",true),HttpStatus.OK);
	}
	
	@GetMapping("/individual/{id}")
	public ResponseEntity<CategoryDto> getController(@PathVariable long id){
		
		return ResponseEntity.ok(categoryServiceImpl.getCategoryService(id));
		
	}
	
	@GetMapping("/AllCategories")
	public ResponseEntity<List<CategoryDto>> getAllController(){
		List<CategoryDto> getAll=categoryServiceImpl.getAllCategoryService();
		return ResponseEntity.ok(getAll);
	}

}
