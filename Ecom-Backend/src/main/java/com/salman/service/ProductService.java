package com.salman.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.salman.payload.ProductDto;
import com.salman.payload.ProductResponse;

public interface ProductService {

	public ProductDto createProduct(ProductDto product, int catid);

	public ProductResponse viewAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDirction);

	public ProductDto getProductById(Integer id);

	public void deleteProduct(Integer id);

	public ProductDto updateProductById(@RequestBody ProductDto product, Integer id);

	// find product by categories
	public List<ProductDto> findProductByCategory(Integer catId);

}
