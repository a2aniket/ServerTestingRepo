/**
 * Controller class for handling CRUD operations on Product resource.
 */
package com.persistent.api;
import com.persistent.model.Product;

import com.persistent.model.Product;
import com.persistent.model.ProductDto;
import com.persistent.service.ProductService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.constraints.*;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;
import com.persistent.util.Constants;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-11-28T04:24:25.988Z[UTC]")
@Controller
@RequestMapping("${openapi.swaggerPetstoreOpenAPI30.base-path:/api/v3}")
public class ProductApiController implements ProductApi {

    private final NativeWebRequest request;

    /**
     * Service instance for database operations.
     */
    @Autowired
    private ProductService productService;

    @Autowired
    public ProductApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    /**
     * Services the addProduct API POST call.
     * @return The Product object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<Product> addProduct(@Parameter(name = "Product", description = "Create a new Product in the store", required = true) @Valid @RequestBody Product product
    ){
        ProductDto productdto = new ProductDto(product);
        ProductDto processedproduct = productService.addProduct(productdto);  
        return new ResponseEntity<Product>(processedproduct.toEntity(),HttpStatus.OK);
    }
    /**
     * Services the deleteProduct API DELETE call.
     * @return The Product object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<Product> deleteProduct(@Parameter(name = "productId", description = "Product id to delete", required = true, in = ParameterIn.PATH) @PathVariable("productId") Long productId
    ){
        ProductDto processedproduct = productService.deleteProduct(productId);  
        return new ResponseEntity<Product>(processedproduct.toEntity(),HttpStatus.OK);
    }
    /**
     * Services the getProduct API GET call.
     * @return The Product object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<Product> getProduct(@Parameter(name = "productId", description = "ID of Product to return", required = true, in = ParameterIn.PATH) @PathVariable("productId") Long productId
    ){
        ProductDto processedproduct = productService.getProduct(productId);  
        return new ResponseEntity<Product>(processedproduct.toEntity(),HttpStatus.OK);
    }
    /**
     * Services the getProductList API GET call.
     * @return The Product object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<List<Product>> getProductList(
        @RequestParam(value = "query_string", defaultValue = Constants.DEFAULT_SEARCH_CRITERIA, required = false) String queryString,
        @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
        @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
        @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        List<ProductDto> productDtoList = productService.getProductList(queryString,pageNumber,pageSize,sortBy,sortDir);
        List<Product> productList = new ArrayList<Product>();
        for(ProductDto productDto : productDtoList){
            productList.add(productDto.toEntity());
        }
        return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
    }
    /**
     * Services the updateProduct API PUT call.
     * @return The Product object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<Product> updateProduct(@Parameter(name = "Product", description = "Update an existent Product in the store", required = true) @Valid @RequestBody Product product
    ){
        ProductDto productdto = new ProductDto(product);
        ProductDto processedproduct = productService.updateProduct(productdto);  
        return new ResponseEntity<Product>(processedproduct.toEntity(),HttpStatus.OK);
    }
}
