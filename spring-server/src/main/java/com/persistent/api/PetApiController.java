/**
 * Controller class for handling CRUD operations on Pet resource.
 */
package com.persistent.api;
import com.persistent.model.Pet;

import com.persistent.model.Pet;
import com.persistent.model.PetDto;
import com.persistent.service.PetService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.constraints.*;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;
import com.persistent.util.Constants;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-11-28T09:05:10.532Z[UTC]")
@Controller
@RequestMapping("${openapi.swaggerPetstoreOpenAPI30.base-path:/api/v3}")
public class PetApiController implements PetApi {

    private final NativeWebRequest request;

    /**
     * Service instance for database operations.
     */
    @Autowired
    private PetService petService;

    @Autowired
    public PetApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    /**
     * Services the addPet API POST call.
     * @return The Pet object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<Pet> addPet(@Parameter(name = "Pet", description = "Create a new pet in the store", required = true) @Valid @RequestBody Pet pet
    ){
        PetDto petdto = new PetDto(pet);
        PetDto processedpet = petService.addPet(petdto);  
        return new ResponseEntity<Pet>(processedpet.toEntity(),HttpStatus.OK);
    }
    /**
     * Services the deletePet API DELETE call.
     * @return The Pet object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<Pet> deletePet(@Parameter(name = "petId", description = "Pet id to delete", required = true, in = ParameterIn.PATH) @PathVariable("petId") Long petId
    ){
        PetDto processedpet = petService.deletePet(petId);  
        return new ResponseEntity<Pet>(processedpet.toEntity(),HttpStatus.OK);
    }
    /**
     * Services the getPet API GET call.
     * @return The Pet object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<Pet> getPet(@Parameter(name = "petId", description = "ID of pet to return", required = true, in = ParameterIn.PATH) @PathVariable("petId") Long petId
    ){
        PetDto processedpet = petService.getPet(petId);  
        return new ResponseEntity<Pet>(processedpet.toEntity(),HttpStatus.OK);
    }
    /**
     * Services the getPetList API GET call.
     * @return The Pet object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<List<Pet>> getPetList(
        @RequestParam(value = "query_string", defaultValue = Constants.DEFAULT_SEARCH_CRITERIA, required = false) String queryString,
        @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
        @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
        @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        List<PetDto> petDtoList = petService.getPetList(queryString,pageNumber,pageSize,sortBy,sortDir);
        List<Pet> petList = new ArrayList<Pet>();
        for(PetDto petDto : petDtoList){
            petList.add(petDto.toEntity());
        }
        return new ResponseEntity<List<Pet>>(petList, HttpStatus.OK);
    }
    /**
     * Services the updatePet API PUT call.
     * @return The Pet object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<Pet> updatePet(@Parameter(name = "Pet", description = "Update an existent pet in the store", required = true) @Valid @RequestBody Pet pet
    ){
        PetDto petdto = new PetDto(pet);
        PetDto processedpet = petService.updatePet(petdto);  
        return new ResponseEntity<Pet>(processedpet.toEntity(),HttpStatus.OK);
    }
}
