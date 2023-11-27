/**
 * Controller class for handling CRUD operations on Category resource.
 */
package com.persistent.api;
import com.persistent.model.Category;

import com.persistent.model.Category;
import com.persistent.model.CategoryDto;
import com.persistent.service.CategoryService;



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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-11-27T06:06:31.537Z[UTC]")
@Controller
@RequestMapping("${openapi.swaggerPetstoreOpenAPI30.base-path:/api/v3}")
public class CategoryApiController implements CategoryApi {

    private final NativeWebRequest request;

    /**
     * Service instance for database operations.
     */
    @Autowired
    private CategoryService categoryService;

    @Autowired
    public CategoryApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    /**
     * Services the addCategory API POST call.
     * @return The Category object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<Category> addCategory(@Parameter(name = "Category", description = "Create a new category in the store", required = true) @Valid @RequestBody Category category
    ){
        CategoryDto categorydto = new CategoryDto(category);
        CategoryDto processedcategory = categoryService.addCategory(categorydto);  
        return new ResponseEntity<Category>(processedcategory.toEntity(),HttpStatus.OK);
    }
    /**
     * Services the deleteCategory API DELETE call.
     * @return The Category object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<Category> deleteCategory(@Parameter(name = "categoryId", description = "Category id to delete", required = true, in = ParameterIn.PATH) @PathVariable("categoryId") Long categoryId
    ){
        CategoryDto processedcategory = categoryService.deleteCategory(categoryId);  
        return new ResponseEntity<Category>(processedcategory.toEntity(),HttpStatus.OK);
    }
    /**
     * Services the getCategory API GET call.
     * @return The Category object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<Category> getCategory(@Parameter(name = "categoryId", description = "ID of category to return", required = true, in = ParameterIn.PATH) @PathVariable("categoryId") Long categoryId
    ){
        CategoryDto processedcategory = categoryService.getCategory(categoryId);  
        return new ResponseEntity<Category>(processedcategory.toEntity(),HttpStatus.OK);
    }
    /**
     * Services the getCategoryList API GET call.
     * @return The Category object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<List<Category>> getCategoryList(
        @RequestParam(value = "query_string", defaultValue = Constants.DEFAULT_SEARCH_CRITERIA, required = false) String queryString,
        @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
        @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
        @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        List<CategoryDto> categoryDtoList = categoryService.getCategoryList(queryString,pageNumber,pageSize,sortBy,sortDir);
        List<Category> categoryList = new ArrayList<Category>();
        for(CategoryDto categoryDto : categoryDtoList){
            categoryList.add(categoryDto.toEntity());
        }
        return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
    }
    /**
     * Services the updateCategory API PUT call.
     * @return The Category object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<Category> updateCategory(@Parameter(name = "Category", description = "Update an existent category in the store", required = true) @Valid @RequestBody Category category
    ){
        CategoryDto categorydto = new CategoryDto(category);
        CategoryDto processedcategory = categoryService.updateCategory(categorydto);  
        return new ResponseEntity<Category>(processedcategory.toEntity(),HttpStatus.OK);
    }
}
