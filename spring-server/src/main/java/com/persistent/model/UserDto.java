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
 * User
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-11-28T04:24:25.988Z[UTC]")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  @JsonProperty("id")
  @Id
  @GeneratedValue(generator = "user_id_seq", strategy = GenerationType.AUTO)
  @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", initialValue = 1 ,allocationSize = 1)
  private Long id;

  @JsonProperty("username")
  private String username;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("email")
  private String email;

  @JsonProperty("password")
  private String password;

  @JsonProperty("phone")
  private String phone;

  
  public UserDto(User user){    
    ObjectMapper mapper = new ObjectMapper();
    try{    
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone = user.getPhone();
    }catch (Exception e) {
        e.printStackTrace();
    }
  }

  public User toEntity(){
    User user = new User();
    ObjectMapper mapper = new ObjectMapper();
    try{    
        user.setId(this.id);
        user.setUsername(this.username);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setPhone(this.phone);
    }catch (Exception e) {
        e.printStackTrace();
    }
    return user;   
  }


}

