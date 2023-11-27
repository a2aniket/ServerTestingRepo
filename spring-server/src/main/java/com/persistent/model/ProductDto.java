package com.persistent.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * Product
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-11-27T06:06:31.537Z[UTC]")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

  @JsonProperty("id")
  @Id
  @GeneratedValue(generator = "product_id_seq", strategy = GenerationType.AUTO)
  @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", initialValue = 1 ,allocationSize = 1)
  private Long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("quantity")
  private Long quantity;

  @JsonProperty("price")
  private Long price;

  
  public ProductDto(Product product){    
    ObjectMapper mapper = new ObjectMapper();
    try{    
        this.id = product.getId();
        this.name = product.getName();
        this.quantity = product.getQuantity();
        this.price = product.getPrice();
    }catch (Exception e) {
        e.printStackTrace();
    }
  }

  public Product toEntity(){
    Product product = new Product();
    ObjectMapper mapper = new ObjectMapper();
    try{    
        product.setId(this.id);
        product.setName(this.name);
        product.setQuantity(this.quantity);
        product.setPrice(this.price);
    }catch (Exception e) {
        e.printStackTrace();
    }
    return product;   
  }


}

