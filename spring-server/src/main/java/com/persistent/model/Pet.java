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
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * Pet
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-11-28T04:24:25.988Z[UTC]")
@Data
public class Pet {

  private Long id;
  
  private String name;
  
  private String breed;
  
  private Long age;
  
  private Long price;
  


}
