/**
 * Service class for handling CRUD operations on Order resource.
 */
package com.persistent.service;
import java.lang.reflect.Field;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import com.persistent.model.OrderDto;

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
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method is used to retrieve the order list from the database.
     * @return On successful operation the order list.
     *         or Warning message as "No order found!".
     * @param pageNumber Page number to be displayed.
     * @param pageSize Number of records to be displayed per page.
     * @param sortBy Column name to sort the list by.
     * @param sortDir Sort direction i.e. ASC or DESC.
     */
    public List<OrderDto> getOrderList(String queryString, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        
        log.info("Getting order list");
        String message = null;
        Sort sort = null;
        
        // Checking and correcting the input values
        pageNumber = PaginationAndSorting.checkPageNumber(pageNumber);
        pageSize = PaginationAndSorting.checkPageSize(pageSize);
        sortBy = PaginationAndSorting.checkColumnName(OrderDto.class,sortBy);
        sortDir = PaginationAndSorting.checkSortDirection(sortDir);
        sort = PaginationAndSorting.setSortObject(sortBy, sortDir);
       
        // Creating the page request object.
        // Page number starts from 0.
        // So, we are decrementing the page number by 1.
        // For example, if page number is 1 then it will be 0.
        Pageable pageable = PageRequest.of(--pageNumber, pageSize, sort);

        List<OrderDto> orderList = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        
        orderList = Searching.generatePredicateList(OrderDto.class,queryString, entityManager,criteriaBuilder, sortBy, sortDir, pageable);
       
        if (orderList == null) {
            message = "No order found!";
            log.warn(message);
        }
        return orderList;
    }

    /**
     * This method is used to retrieve a order with the specified ID.
     * @param id The ID of the order to retrieve.
     * @return On successful operation the order with specified ID.
     * @throws InvalidIdException If incorrect ID is provided.
     * @throws ResourceNotFoundException If the order object is not found. 
     */
    public OrderDto getOrder(Long id) {
        log.info("Getting order with id {}", id);

        if(id < 1) {
            String message = "Invalid id : "+ id;
            log.error(message);
            throw new InvalidIdException(message);
        }

        Optional<OrderDto> order = orderRepository.findById(id);
        
        if(order.isPresent()) {
            return order.get();
        } else {
            String message = "Order with id " + id + " is not present!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    /**
     * This method adds a new order to the database with given information.
     * @param order The order object that needs to be added.
     * @return On successful operation the added order is returned.
     */
    public OrderDto addOrder(OrderDto order ) {
        log.info("Adding order");
        return orderRepository.save(order);
    }

    /**
     * This method deletes the order to the database with given information.
     * @param id The id of order that needs to be deleted.
     * @return On successful operation the deleted order is returned.
     * @throws InvalidIdException If incorrect ID is provided.
     * @throws ResourceNotFoundException If the order object is not found.
     */
    public OrderDto deleteOrder(Long id) {
        log.info("Started processing of delete operation");

        if(id < 1) {
            String message = "Invalid id : "+id;
            log.error(message);
            throw new InvalidIdException(message);
        }

        Optional<OrderDto> order = orderRepository.findById(id);
        
        if(order.isPresent()) {
            orderRepository.deleteById(id);
            log.info("Successfully completed the deletion operation for ID : {}",id);
            return order.get();
        } else {
            String message = "Order with id "+id+" is not present!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    /**
     * This method updates an existing order in the database.
     * @param order The order object that needs to be updated.
     * @return On successful operation the updated order object is returned.
     * @throws ResourceNotFoundException If the order object is not found.
     */
    public OrderDto updateOrder(OrderDto order ) {
        log.info("Updating order with id {}",order.getId());
        long id = order.getId();

        if(id < 1) {
            String message = "Invalid id : "+id;
            log.error(message);
            throw new InvalidIdException(message);
        }

        Optional<OrderDto> orderToBeUpdated = orderRepository.findById(id);
        
        if(orderToBeUpdated.isPresent()) {
            return orderRepository.save(order);
        } else {
            String message = "Order with id "+id+" is not present!";
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

}
