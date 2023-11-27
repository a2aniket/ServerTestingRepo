/**
 * Service class for handling CRUD operations on Pet resource.
 */
package com.persistent.service;
import java.lang.reflect.Field;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import com.persistent.model.PetDto;

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
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method is used to retrieve the pet list from the database.
     * @return On successful operation the pet list.
     *         or Warning message as "No pet found!".
     * @param pageNumber Page number to be displayed.
     * @param pageSize Number of records to be displayed per page.
     * @param sortBy Column name to sort the list by.
     * @param sortDir Sort direction i.e. ASC or DESC.
     */
    public List<PetDto> getPetList(String queryString, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        
        log.info("Getting pet list");
        String message = null;
        Sort sort = null;
        
        // Checking and correcting the input values
        pageNumber = PaginationAndSorting.checkPageNumber(pageNumber);
        pageSize = PaginationAndSorting.checkPageSize(pageSize);
        sortBy = PaginationAndSorting.checkColumnName(PetDto.class,sortBy);
        sortDir = PaginationAndSorting.checkSortDirection(sortDir);
        sort = PaginationAndSorting.setSortObject(sortBy, sortDir);
       
        // Creating the page request object.
        // Page number starts from 0.
        // So, we are decrementing the page number by 1.
        // For example, if page number is 1 then it will be 0.
        Pageable pageable = PageRequest.of(--pageNumber, pageSize, sort);

        List<PetDto> petList = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        
        petList = Searching.generatePredicateList(PetDto.class,queryString, entityManager,criteriaBuilder, sortBy, sortDir, pageable);
       
        if (petList == null) {
            message = "No pet found!";
            log.warn(message);
        }
        return petList;
    }

    /**
     * This method is used to retrieve a pet with the specified ID.
     * @param id The ID of the pet to retrieve.
     * @return On successful operation the pet with specified ID.
     * @throws InvalidIdException If incorrect ID is provided.
     * @throws ResourceNotFoundException If the pet object is not found. 
     */
    public PetDto getPet(Long id) {
        log.info("Getting pet with id {}", id);

        if(id < 1) {
            String message = "Invalid id : "+ id;
            log.error(message);
            throw new InvalidIdException(message);
        }

        Optional<PetDto> pet = petRepository.findById(id);
        
        if(pet.isPresent()) {
            return pet.get();
        } else {
            String message = "Pet with id " + id + " is not present!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    /**
     * This method adds a new pet to the database with given information.
     * @param pet The pet object that needs to be added.
     * @return On successful operation the added pet is returned.
     */
    public PetDto addPet(PetDto pet ) {
        log.info("Adding pet");
        return petRepository.save(pet);
    }

    /**
     * This method deletes the pet to the database with given information.
     * @param id The id of pet that needs to be deleted.
     * @return On successful operation the deleted pet is returned.
     * @throws InvalidIdException If incorrect ID is provided.
     * @throws ResourceNotFoundException If the pet object is not found.
     */
    public PetDto deletePet(Long id) {
        log.info("Started processing of delete operation");

        if(id < 1) {
            String message = "Invalid id : "+id;
            log.error(message);
            throw new InvalidIdException(message);
        }

        Optional<PetDto> pet = petRepository.findById(id);
        
        if(pet.isPresent()) {
            petRepository.deleteById(id);
            log.info("Successfully completed the deletion operation for ID : {}",id);
            return pet.get();
        } else {
            String message = "Pet with id "+id+" is not present!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    /**
     * This method updates an existing pet in the database.
     * @param pet The pet object that needs to be updated.
     * @return On successful operation the updated pet object is returned.
     * @throws ResourceNotFoundException If the pet object is not found.
     */
    public PetDto updatePet(PetDto pet ) {
        log.info("Updating pet with id {}",pet.getId());
        long id = pet.getId();

        if(id < 1) {
            String message = "Invalid id : "+id;
            log.error(message);
            throw new InvalidIdException(message);
        }

        Optional<PetDto> petToBeUpdated = petRepository.findById(id);
        
        if(petToBeUpdated.isPresent()) {
            return petRepository.save(pet);
        } else {
            String message = "Pet with id "+id+" is not present!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

}
