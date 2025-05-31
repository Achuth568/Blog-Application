package com.blog.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entity.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repositories.CategoryRepository;
import com.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService  {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public CategoryDto createCategoryService(CategoryDto categoryDto) {
		
		Category category=modelMapper.map(categoryDto,Category.class);
		
		Category savedCategory=categoryRepository.save(category);
		
		return modelMapper.map(savedCategory,CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategoryService(CategoryDto categoryDto, long id) {
		Category category=categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category", "Id", id));
		
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		
		Category updatedCategory=categoryRepository.save(category);
			
		return modelMapper.map(updatedCategory,CategoryDto.class);
	}

	@Override
	public void deleteCategoryService(long id) {
		Category category=categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category", "Id", id));
		categoryRepository.delete(category);
	}

	@Override
	public CategoryDto getCategoryService(long id) {
		
		Category category=categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category", "Id", id));
		
		return modelMapper.map(category,CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategoryService() {
		
		List<Category> categoryList=categoryRepository.findAll();
		
		return  categoryList.stream()
				.map(cat-> modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
	}

}
