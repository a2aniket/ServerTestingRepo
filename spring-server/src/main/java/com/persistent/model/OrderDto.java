package com.persistent.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
/**
 * Order
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-11-27T06:06:31.537Z[UTC]")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

  @JsonProperty("id")
  @Id
  @GeneratedValue(generator = "order_id_seq", strategy = GenerationType.AUTO)
  @SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq", initialValue = 1 ,allocationSize = 1)
  private Long id;

  @JsonProperty("quantity")
  private Integer quantity;

  @JsonProperty("shipDate")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime shipDate;

  @JsonProperty("complete")
  private Boolean complete;

  
  public OrderDto(Order order){    
    ObjectMapper mapper = new ObjectMapper();
    try{    
        this.id = order.getId();
        this.quantity = order.getQuantity();
        this.shipDate = order.getShipDate();
        this.complete = order.getComplete();
    }catch (Exception e) {
        e.printStackTrace();
    }
  }

  public Order toEntity(){
    Order order = new Order();
    ObjectMapper mapper = new ObjectMapper();
    try{    
        order.setId(this.id);
        order.setQuantity(this.quantity);
        order.setShipDate(this.shipDate);
        order.setComplete(this.complete);
    }catch (Exception e) {
        e.printStackTrace();
    }
    return order;   
  }


}

