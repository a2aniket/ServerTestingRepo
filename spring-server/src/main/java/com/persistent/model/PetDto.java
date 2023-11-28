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
 * Pet
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-11-28T09:05:10.532Z[UTC]")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PetDto {

  @JsonProperty("id")
  @Id
  @GeneratedValue(generator = "pet_id_seq", strategy = GenerationType.AUTO)
  @SequenceGenerator(name = "pet_id_seq", sequenceName = "pet_id_seq", initialValue = 1 ,allocationSize = 1)
  private Long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("breed")
  private String breed;

  @JsonProperty("age")
  private Long age;

  @JsonProperty("price")
  private Long price;

  
  public PetDto(Pet pet){    
    ObjectMapper mapper = new ObjectMapper();
    try{    
        this.id = pet.getId();
        this.name = pet.getName();
        this.breed = pet.getBreed();
        this.age = pet.getAge();
        this.price = pet.getPrice();
    }catch (Exception e) {
        e.printStackTrace();
    }
  }

  public Pet toEntity(){
    Pet pet = new Pet();
    ObjectMapper mapper = new ObjectMapper();
    try{    
        pet.setId(this.id);
        pet.setName(this.name);
        pet.setBreed(this.breed);
        pet.setAge(this.age);
        pet.setPrice(this.price);
    }catch (Exception e) {
        e.printStackTrace();
    }
    return pet;   
  }


}

