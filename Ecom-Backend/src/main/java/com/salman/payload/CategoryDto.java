package com.salman.payload;

import java.util.Set;



public class CategoryDto {

	private int categoryId;
	private String title;

	// mapping
	private Set<ProductDto> productDto;

	public Set<ProductDto> getProductDto(Set<ProductDto> productDto) {
		return productDto;
	}

	public void setProductDto(Set<ProductDto> productDto) {
		this.productDto = productDto;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public CategoryDto(int categoryId, String title) {
		super();
		this.categoryId = categoryId;
		this.title = title;
	}

	public CategoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
