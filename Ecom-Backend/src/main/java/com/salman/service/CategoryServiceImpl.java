package com.salman.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salman.exception.ResourceNotFoundException;
import com.salman.model.Category;
import com.salman.payload.CategoryDto;
import com.salman.repository.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo catRepo;
	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDto create(CategoryDto cDto) {
		// CategoryDto to Category convert //use modelmapper dependency
		Category cat = mapper.map(cDto, Category.class);
		Category save = catRepo.save(cat);
		// category to categoryDto convert
		return mapper.map(save, CategoryDto.class);
	}

	@Override
	public CategoryDto update(CategoryDto cDto, Integer id) {
		Category cat= this.catRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("This Category Id not Found"));

		cat.setTitle(cDto.getTitle());
		Category save = catRepo.save(cat);

		return mapper.map(save, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		Category Cat= this.catRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("This Category Id not Found"));
		this.catRepo.delete(Cat);

	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category getById = catRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("This Category Id not Found"));
		return mapper.map(getById, CategoryDto.class);

	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> findAll = catRepo.findAll();
		List<CategoryDto> allDto = findAll.stream().map(cat -> mapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		return allDto;
	}

}
