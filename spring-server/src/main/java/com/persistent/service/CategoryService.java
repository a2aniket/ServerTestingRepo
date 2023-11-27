/**
 * Service class for handling CRUD operations on Category resource.
 */
package com.persistent.service;
import java.lang.reflect.Field;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import com.persistent.model.CategoryDto;

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
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method is used to retrieve the category list from the database.
     * @return On successful operation the category list.
     *         or Warning message as "No category found!".
     * @param pageNumber Page number to be displayed.
     * @param pageSize Number of records to be displayed per page.
     * @param sortBy Column name to sort the list by.
     * @param sortDir Sort direction i.e. ASC or DESC.
     */
    public List<CategoryDto> getCategoryList(String queryString, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        
        log.info("Getting category list");
        String message = null;
        Sort sort = null;
        
        // Checking and correcting the input values
        pageNumber = PaginationAndSorting.checkPageNumber(pageNumber);
        pageSize = PaginationAndSorting.checkPageSize(pageSize);
        sortBy = PaginationAndSorting.checkColumnName(CategoryDto.class,sortBy);
        sortDir = PaginationAndSorting.checkSortDirection(sortDir);
        sort = PaginationAndSorting.setSortObject(sortBy, sortDir);
       
        // Creating the page request object.
        // Page number starts from 0.
        // So, we are decrementing the page number by 1.
        // For example, if page number is 1 then it will be 0.
        Pageable pageable = PageRequest.of(--pageNumber, pageSize, sort);

        List<CategoryDto> categoryList = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        
        categoryList = Searching.generatePredicateList(CategoryDto.class,queryString, entityManager,criteriaBuilder, sortBy, sortDir, pageable);
       
        if (categoryList == null) {
            message = "No category found!";
            log.warn(message);
        }
        return categoryList;
    }

    /**
     * This method is used to retrieve a category with the specified ID.
     * @param id The ID of the category to retrieve.
     * @return On successful operation the category with specified ID.
     * @throws InvalidIdException If incorrect ID is provided.
     * @throws ResourceNotFoundException If the category object is not found. 
     */
    public CategoryDto getCategory(Long id) {
        log.info("Getting category with id {}", id);

        if(id < 1) {
            String message = "Invalid id : "+ id;
            log.error(message);
            throw new InvalidIdException(message);
        }

        Optional<CategoryDto> category = categoryRepository.findById(id);
        
        if(category.isPresent()) {
            return category.get();
        } else {
            String message = "Category with id " + id + " is not present!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    /**
     * This method adds a new category to the database with given information.
     * @param category The category object that needs to be added.
     * @return On successful operation the added category is returned.
     */
    public CategoryDto addCategory(CategoryDto category ) {
        log.info("Adding category");
        return categoryRepository.save(category);
    }

    /**
     * This method deletes the category to the database with given information.
     * @param id The id of category that needs to be deleted.
     * @return On successful operation the deleted category is returned.
     * @throws InvalidIdException If incorrect ID is provided.
     * @throws ResourceNotFoundException If the category object is not found.
     */
    public CategoryDto deleteCategory(Long id) {
        log.info("Started processing of delete operation");

        if(id < 1) {
            String message = "Invalid id : "+id;
            log.error(message);
            throw new InvalidIdException(message);
        }

        Optional<CategoryDto> category = categoryRepository.findById(id);
        
        if(category.isPresent()) {
            categoryRepository.deleteById(id);
            log.info("Successfully completed the deletion operation for ID : {}",id);
            return category.get();
        } else {
            String message = "Category with id "+id+" is not present!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    /**
     * This method updates an existing category in the database.
     * @param category The category object that needs to be updated.
     * @return On successful operation the updated category object is returned.
     * @throws ResourceNotFoundException If the category object is not found.
     */
    public CategoryDto updateCategory(CategoryDto category ) {
        log.info("Updating category with id {}",category.getId());
        long id = category.getId();

        if(id < 1) {
            String message = "Invalid id : "+id;
            log.error(message);
            throw new InvalidIdException(message);
        }

        Optional<CategoryDto> categoryToBeUpdated = categoryRepository.findById(id);
        
        if(categoryToBeUpdated.isPresent()) {
            return categoryRepository.save(category);
        } else {
            String message = "Category with id "+id+" is not present!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

}
