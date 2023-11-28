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
 * Category
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-11-28T09:05:10.532Z[UTC]")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

  @JsonProperty("id")
  @Id
  @GeneratedValue(generator = "category_id_seq", strategy = GenerationType.AUTO)
  @SequenceGenerator(name = "category_id_seq", sequenceName = "category_id_seq", initialValue = 1 ,allocationSize = 1)
  private Long id;

  @JsonProperty("name")
  private String name;

  
  public CategoryDto(Category category){    
    ObjectMapper mapper = new ObjectMapper();
    try{    
        this.id = category.getId();
        this.name = category.getName();
    }catch (Exception e) {
        e.printStackTrace();
    }
  }

  public Category toEntity(){
    Category category = new Category();
    ObjectMapper mapper = new ObjectMapper();
    try{    
        category.setId(this.id);
        category.setName(this.name);
    }catch (Exception e) {
        e.printStackTrace();
    }
    return category;   
  }


}

