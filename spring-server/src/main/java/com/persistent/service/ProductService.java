/**
 * Service class for handling CRUD operations on Product resource.
 */
package com.persistent.service;
import java.lang.reflect.Field;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import com.persistent.model.ProductDto;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;
import com.persistent.exception.InvalidIdException;
import com.persistent.exception.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import com.persistent.util.Constants;


@Service
@Log4j2
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method is used to retrieve the product list from the database.
     * @return On successful operation the product list.
     *         or Warning message as "No product found!".
     * @param pageNumber Page number to be displayed.
     * @param pageSize Number of records to be displayed per page.
     * @param sortBy Column name to sort the list by.
     * @param sortDir Sort direction i.e. ASC or DESC.
     */
    public List<ProductDto> getProductList(String queryString, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        
        log.info("Getting product list");
        String message = null;
        Sort sort = null;
        
        // Checking and correcting the input values
        pageNumber = PaginationAndSorting.checkPageNumber(pageNumber);
        pageSize = PaginationAndSorting.checkPageSize(pageSize);
        sortBy = PaginationAndSorting.checkColumnName(ProductDto.class,sortBy);
        sortDir = PaginationAndSorting.checkSortDirection(sortDir);
        sort = PaginationAndSorting.setSortObject(sortBy, sortDir);
       
        // Creating the page request object.
        // Page number starts from 0.
        // So, we are decrementing the page number by 1.
        // For example, if page number is 1 then it will be 0.
        Pageable pageable = PageRequest.of(--pageNumber, pageSize, sort);

        List<ProductDto> productList = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        
        productList = Searching.generatePredicateList(ProductDto.class,queryString, entityManager,criteriaBuilder, sortBy, sortDir, pageable);
       
        if (productList == null) {
            message = "No product found!";
            log.warn(message);
        }
        return productList;
    }

    /**
     * This method is used to retrieve a product with the specified ID.
     * @param id The ID of the product to retrieve.
     * @return On successful operation the product with specified ID.
     * @throws InvalidIdException If incorrect ID is provided.
     * @throws ResourceNotFoundException If the product object is not found. 
     */
    public ProductDto getProduct(Long id) {
        log.info("Getting product with id {}", id);

        if(id < 1) {
            String message = "Invalid id : "+ id;
            log.error(message);
            throw new InvalidIdException(message);
        }

        Optional<ProductDto> product = productRepository.findById(id);
        
        if(product.isPresent()) {
            return product.get();
        } else {
            String message = "Product with id " + id + " is not present!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    /**
     * This method adds a new product to the database with given information.
     * @param product The product object that needs to be added.
     * @return On successful operation the added product is returned.
     */
    public ProductDto addProduct(ProductDto product ) {
        log.info("Adding product");
        return productRepository.save(product);
    }

    /**
     * This method deletes the product to the database with given information.
     * @param id The id of product that needs to be deleted.
     * @return On successful operation the deleted product is returned.
     * @throws InvalidIdException If incorrect ID is provided.
     * @throws ResourceNotFoundException If the product object is not found.
     */
    public ProductDto deleteProduct(Long id) {
        log.info("Started processing of delete operation");

        if(id < 1) {
            String message = "Invalid id : "+id;
            log.error(message);
            throw new InvalidIdException(message);
        }

        Optional<ProductDto> product = productRepository.findById(id);
        
        if(product.isPresent()) {
            productRepository.deleteById(id);
            log.info("Successfully completed the deletion operation for ID : {}",id);
            return product.get();
        } else {
            String message = "Product with id "+id+" is not present!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    /**
     * This method updates an existing product in the database.
     * @param product The product object that needs to be updated.
     * @return On successful operation the updated product object is returned.
     * @throws ResourceNotFoundException If the product object is not found.
     */
    public ProductDto updateProduct(ProductDto product ) {
        log.info("Updating product with id {}",product.getId());
        long id = product.getId();

        if(id < 1) {
            String message = "Invalid id : "+id;
            log.error(message);
            throw new InvalidIdException(message);
        }

        Optional<ProductDto> productToBeUpdated = productRepository.findById(id);
        
        if(productToBeUpdated.isPresent()) {
            return productRepository.save(product);
        } else {
            String message = "Product with id "+id+" is not present!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

}
