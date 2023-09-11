package com.salman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salman.model.Category;
import com.salman.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{

	List<Product> findByCategory(Category category);
}
