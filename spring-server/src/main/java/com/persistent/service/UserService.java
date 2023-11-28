/**
 * Service class for handling CRUD operations on User resource.
 */
package com.persistent.service;
import java.lang.reflect.Field;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import com.persistent.model.UserDto;

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
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method is used to retrieve the user list from the database.
     * @return On successful operation the user list.
     *         or Warning message as "No user found!".
     * @param pageNumber Page number to be displayed.
     * @param pageSize Number of records to be displayed per page.
     * @param sortBy Column name to sort the list by.
     * @param sortDir Sort direction i.e. ASC or DESC.
     */
    public List<UserDto> getUserList(String queryString, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        
        log.info("Getting user list");
        String message = null;
        Sort sort = null;
        
        // Checking and correcting the input values
        pageNumber = PaginationAndSorting.checkPageNumber(pageNumber);
        pageSize = PaginationAndSorting.checkPageSize(pageSize);
        sortBy = PaginationAndSorting.checkColumnName(UserDto.class,sortBy);
        sortDir = PaginationAndSorting.checkSortDirection(sortDir);
        sort = PaginationAndSorting.setSortObject(sortBy, sortDir);
       
        // Creating the page request object.
        // Page number starts from 0.
        // So, we are decrementing the page number by 1.
        // For example, if page number is 1 then it will be 0.
        Pageable pageable = PageRequest.of(--pageNumber, pageSize, sort);

        List<UserDto> userList = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        
        userList = Searching.generatePredicateList(UserDto.class,queryString, entityManager,criteriaBuilder, sortBy, sortDir, pageable);
       
        if (userList == null) {
            message = "No user found!";
            log.warn(message);
        }
        return userList;
    }

    /**
     * This method is used to retrieve a user with the specified ID.
     * @param id The ID of the user to retrieve.
     * @return On successful operation the user with specified ID.
     * @throws InvalidIdException If incorrect ID is provided.
     * @throws ResourceNotFoundException If the user object is not found. 
     */
    public UserDto getUser(Long id) {
        log.info("Getting user with id {}", id);

        if(id < 1) {
            String message = "Invalid id : "+ id;
            log.error(message);
            throw new InvalidIdException(message);
        }

        Optional<UserDto> user = userRepository.findById(id);
        
        if(user.isPresent()) {
            return user.get();
        } else {
            String message = "User with id " + id + " is not present!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    /**
     * This method adds a new user to the database with given information.
     * @param user The user object that needs to be added.
     * @return On successful operation the added user is returned.
     */
    public UserDto addUser(UserDto user ) {
        log.info("Adding user");
        return userRepository.save(user);
    }

    /**
     * This method deletes the user to the database with given information.
     * @param id The id of user that needs to be deleted.
     * @return On successful operation the deleted user is returned.
     * @throws InvalidIdException If incorrect ID is provided.
     * @throws ResourceNotFoundException If the user object is not found.
     */
    public UserDto deleteUser(Long id) {
        log.info("Started processing of delete operation");

        if(id < 1) {
            String message = "Invalid id : "+id;
            log.error(message);
            throw new InvalidIdException(message);
        }

        Optional<UserDto> user = userRepository.findById(id);
        
        if(user.isPresent()) {
            userRepository.deleteById(id);
            log.info("Successfully completed the deletion operation for ID : {}",id);
            return user.get();
        } else {
            String message = "User with id "+id+" is not present!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    /**
     * This method updates an existing user in the database.
     * @param user The user object that needs to be updated.
     * @return On successful operation the updated user object is returned.
     * @throws ResourceNotFoundException If the user object is not found.
     */
    public UserDto updateUser(UserDto user ) {
        log.info("Updating user with id {}",user.getId());
        long id = user.getId();

        if(id < 1) {
            String message = "Invalid id : "+id;
            log.error(message);
            throw new InvalidIdException(message);
        }

        Optional<UserDto> userToBeUpdated = userRepository.findById(id);
        
        if(userToBeUpdated.isPresent()) {
            return userRepository.save(user);
        } else {
            String message = "User with id "+id+" is not present!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

}
