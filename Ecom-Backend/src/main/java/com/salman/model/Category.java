package com.salman.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int categoryId;
	private String title;

	/////////////////////////////////////////////////////////////////
	// mapping
	@OneToMany
	(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Product> product;
	/////////////////////////////////////////////////////////////////
	

	public Set<Product> getProduct(Set<Product> product) {
		return product;
	}

	public void setProduct(Set<Product> product) {
		this.product = product;
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

	public Category(int categoryId, String title) {
		super();
		this.categoryId = categoryId;
		this.title = title;
	}

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

}
