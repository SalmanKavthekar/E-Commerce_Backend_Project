package com.salman.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.salman.exception.ResourceNotFoundException;
import com.salman.model.Category;
import com.salman.model.Product;
import com.salman.payload.CategoryDto;
import com.salman.payload.ProductDto;
import com.salman.payload.ProductResponse;
import com.salman.repository.CategoryRepo;
import com.salman.repository.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo pRepo;

	@Autowired
	private CategoryRepo catRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public ProductDto createProduct(ProductDto productDto, int catid) {
		// fetch category availabole or not using id
		Category cat = this.catRepo.findById(catid).orElseThrow(()->new ResourceNotFoundException("This Category id not found Catgory"));

		// conversion productDto to product
		Product product = toEntity(productDto);
		// set categaries
		product.setCategory(cat);
		Product save = pRepo.save(product);

		// Product save = pRepo.save(product);
		// conversion product to productDto
		ProductDto dto = toDto(save);
		return dto;
	}

	@Override
	public ProductResponse viewAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDirction) {
		Sort sort = null;
		if (sortDirction.trim().toLowerCase().equals("asc")) {
			sort = Sort.by(sortBy).ascending();
			System.out.println(sort);
		} else {
			sort = Sort.by(sortBy).descending();
			System.out.println(sort);

		}
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page =pRepo.findAll(pageable);
		List<Product> pageProduct=page.getContent();
		//filter product and put in page only live product show in page
		List<Product>  product=pageProduct.stream().filter(p -> p.isLive()).collect(Collectors.toList());
		//convert into product into productDto
		List<ProductDto> productDto=product.stream().map(p -> toDto(p)).collect(Collectors.toList());
		
		ProductResponse response=new ProductResponse();
		response.setContent(productDto);
		response.setPageNumber(page.getNumber());
		response.setPageSize(page.getSize());
		response.setTotalPages(page.getTotalPages());
		response.setLastPage(page.isLast());

		//List<Product> findAll = pRepo.findAll();
		// product to productDto conversion
		//List<ProductDto> findAllDto = findAll.stream().map(product -> this.toDto(product)).collect(Collectors.toList());
		return response;
		
	}

	@Override
	public ProductDto getProductById(Integer id) {
		  Product findById = pRepo.findById(id).orElseThrow(()->new ResourceNotFoundException(id+" from this product id product not found"));
		ProductDto dto = this.toDto(findById);
		return dto;
	}

	@Override
	public void deleteProduct(Integer id) {
		Product byId = pRepo.findById(id).orElseThrow(()->new ResourceNotFoundException(id+" from this product id product not found"));
		pRepo.delete(byId);
	}

	@Override
	public ProductDto updateProductById(ProductDto product, Integer id) {
        Product p= pRepo.findById(id).orElseThrow(()->new ResourceNotFoundException(id+" product Not found"));

		p.setImageName(product.getImageName());
		p.setLive(product.isLive());
		p.setProductDisc(product.getProductDisc());
		p.setProductName(product.getProductName());
		p.setProductPrize(product.getProductPrize());
		p.setProductQuantity(product.getProductQuantity());
		p.setStock(product.isStock());

		Product save = pRepo.save(p);
		ProductDto pd = toDto(save);
		return pd;

	}
	
	//find product by categories
	public List<ProductDto> findProductByCategory(Integer catId){
		Category cat=catRepo.findById(catId).orElseThrow(() -> new ResourceNotFoundException("this id Category not Found"));
		List<Product> findByCategory =pRepo.findByCategory(cat);
		List<ProductDto> collect=findByCategory.stream().map(product -> toDto(product)).collect(Collectors.toList());
		return collect;
	}

	// productDto to product conversion
	public Product toEntity(ProductDto pDto) {
//		Product pr = new Product();
//		pr.setImageName(pDto.getImageName());
//		pr.setLive(pDto.isLive());
//		pr.setProductDisc(pDto.getProductDisc());
//		pr.setProductId(pDto.getProductId());
//		pr.setProductName(pDto.getProductName());
//		pr.setProductPrize(pDto.getProductPrize());
//		pr.setProductQuantity(pDto.getProductQuantity());
//		pr.setStock(pDto.isStock());

		return mapper.map(pDto, Product.class);
	}

	// product to productDto convert
	public ProductDto toDto(Product product) {
		ProductDto pDto = new ProductDto();
		pDto.setProductId(product.getProductId());
		pDto.setImageName(product.getImageName());
		pDto.setLive(product.isLive());
		pDto.setProductDisc(product.getProductDisc());
		pDto.setProductName(product.getProductName());
		pDto.setProductPrize(product.getProductPrize());
		pDto.setProductQuantity(product.getProductQuantity());
		pDto.setStock(product.isStock());

		// set categori in productDto after mapping
		// change category to categoryDto
		CategoryDto catDto = new CategoryDto();
		catDto.setCategoryId(product.getCategory().getCategoryId());
		catDto.setTitle(product.getCategory().getTitle());

		// then set CategoryDto in ProductDto
		pDto.setCategory(catDto);

		return pDto;
	}

}
