/**
 * Repository interface for performing database operations using JPA.
 */
package com.persistent.service;

import com.persistent.model.OrderDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends JpaRepository<OrderDto, Long> {
    
}