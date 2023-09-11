package com.salman.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salman.payload.ApiResponse;
import com.salman.payload.CategoryDto;
import com.salman.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService catService;

	// CREATE CATEGORY
	@PostMapping("/create")
	public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto catDto) {
		CategoryDto create = catService.create(catDto);
		return new ResponseEntity<CategoryDto>(create, HttpStatus.CREATED);
	}

	// UPDATE CATEGORY
	@PostMapping("/updateById/{id}")
	public ResponseEntity<CategoryDto> update(@RequestBody CategoryDto catDto,@PathVariable Integer id) {
		CategoryDto update = catService.update(catDto, id);
		return new ResponseEntity<CategoryDto>(update, HttpStatus.ACCEPTED);

	}

	// delete category
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
		catService.deleteCategory(id);
		return new ResponseEntity<ApiResponse>
		(new ApiResponse("Category delete successfully", true), HttpStatus.OK);
	}
	
	
	//get categories by id
	@GetMapping("/getById/{id}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id){
		CategoryDto updateById=catService.getCategoryById(id);
		return new ResponseEntity<CategoryDto>(updateById, HttpStatus.OK);
		
	}
	
	//get all category
	@GetMapping("/getAll")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		List<CategoryDto> listCategory=catService.getAllCategory();
		return new ResponseEntity<List<CategoryDto>>(listCategory, HttpStatus.OK);
	}

}
