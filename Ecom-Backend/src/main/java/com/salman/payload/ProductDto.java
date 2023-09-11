package com.salman.payload;


public class ProductDto {

	private int productId;
	private String productName;
	private double productPrize;
	private boolean stock;
	private int productQuantity;
	private boolean live;
	private String productDisc;
	private String imageName;

	// mapping
	
	private CategoryDto category;

	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto catDto) {
		this.category = catDto;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public ProductDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductDto(int productId, String productName, double productPrize, boolean stock, int productQuantity,
			boolean live, String productDisc, String imageName) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productPrize = productPrize;
		this.stock = stock;
		this.productQuantity = productQuantity;
		this.live = live;
		this.productDisc = productDisc;
		this.imageName = imageName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrize() {
		return productPrize;
	}

	public void setProductPrize(double productPrize) {
		this.productPrize = productPrize;
	}

	public boolean isStock() {
		return stock;
	}

	public void setStock(boolean stock) {
		this.stock = stock;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public String getProductDisc() {
		return productDisc;
	}

	public void setProductDisc(String productDisc) {
		this.productDisc = productDisc;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}
