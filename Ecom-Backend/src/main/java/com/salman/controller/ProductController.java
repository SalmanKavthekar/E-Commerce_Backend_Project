package com.salman.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.salman.payload.ApiResponse;
import com.salman.payload.AppConstants;
import com.salman.payload.ProductDto;
import com.salman.payload.ProductResponse;
import com.salman.service.FileUpload;
import com.salman.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService pService;
	
	@Autowired
	private FileUpload  fileUpload;
	
	@Value("${product.path.images}")   //app.properties
	private String imagePath;
	
	
	//Image upload
	@PostMapping("/images/{productId}")
	public ResponseEntity<?> uploadImageOfProduct(@PathVariable Integer productId,
								@RequestParam("product_image")MultipartFile file) {
		
		ProductDto product=pService.getProductById(productId);
		String imageName=null;
		
		try {
			String uploadImage=fileUpload.uploadImage(imagePath, file);
			product.setImageName(uploadImage);
			ProductDto updateProduct=pService.updateProductById(product, productId);
			return new ResponseEntity<>(updateProduct, HttpStatus.ACCEPTED);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(Map.of("Message","File Not Upload On Server"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/createProduct/{catid}")
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto product, @PathVariable Integer catid ){
		ProductDto p=pService.createProduct(product, catid);
		return new ResponseEntity<ProductDto>(p, HttpStatus.CREATED);
	}
	
	@GetMapping("/viewAllProduct")
	public ProductResponse viewAll(@RequestParam(value="pageNumber", 
												defaultValue = AppConstants.PAGE_NUMBER_STRING, 
												required = false) Integer pageNumber,
												@RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE_STRING,
												required = false)Integer pageSize,
												@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_STRING,
												required = false)String sortBy,
												@RequestParam(value = "sortDirction", defaultValue = AppConstants.SORT_DIRECTION_STRING,
												required = false)String sortDirction){
		ProductResponse response=pService.viewAllProduct(pageNumber,pageSize,sortBy,sortDirction);
		return response;
	}
	
	@GetMapping("/viewProductById/{id}")
	public ResponseEntity<ProductDto> productById(@PathVariable Integer id){
		ProductDto pid=pService.getProductById(id);
		return new ResponseEntity<ProductDto>(pid, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Integer id) {
		pService.deleteProduct(id);
		return new ResponseEntity<ApiResponse>
		(new ApiResponse("Product Deleted Successfully", true), HttpStatus.OK);
	}
	 
	@PutMapping("/updateProduct/{id}")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto product, @PathVariable Integer id) {
		ProductDto pUpdate = pService.updateProductById(product, id);
		return new ResponseEntity<ProductDto>(pUpdate, HttpStatus.ACCEPTED);
	}
	
	
	//find product by Categorywise
	@GetMapping("/findByCategory/{catId}")
	public ResponseEntity<List<ProductDto>> getProductByCategory(@PathVariable Integer catId){
		List<ProductDto> findProductByCategory =pService.findProductByCategory(catId);
		return new ResponseEntity<List<ProductDto>>(findProductByCategory, HttpStatus.ACCEPTED);
	}
	

}

















