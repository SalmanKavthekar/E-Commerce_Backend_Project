package com.salman.service;

import java.util.List;

import com.salman.payload.CategoryDto;

public interface CategoryService {

	public CategoryDto create(CategoryDto cDto);
	
	public CategoryDto update(CategoryDto cDto, Integer id);
	
	public void deleteCategory(Integer categoryId);
	
	public CategoryDto getCategoryById(Integer categoryId);
	
	public List<CategoryDto> getAllCategory();
	
	
}
