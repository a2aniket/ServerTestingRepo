/**
 * Controller class for handling CRUD operations on User resource.
 */
package com.persistent.api;
import com.persistent.model.User;

import com.persistent.model.User;
import com.persistent.model.UserDto;
import com.persistent.service.UserService;



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
public class UserApiController implements UserApi {

    private final NativeWebRequest request;

    /**
     * Service instance for database operations.
     */
    @Autowired
    private UserService userService;

    @Autowired
    public UserApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    /**
     * Services the addUser API POST call.
     * @return The User object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<User> addUser(@Parameter(name = "User", description = "Create a new user in the user", required = true) @Valid @RequestBody User user
    ){
        UserDto userdto = new UserDto(user);
        UserDto processeduser = userService.addUser(userdto);  
        return new ResponseEntity<User>(processeduser.toEntity(),HttpStatus.OK);
    }
    /**
     * Services the deleteUser API DELETE call.
     * @return The User object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<User> deleteUser(@Parameter(name = "userId", description = "User id to delete", required = true, in = ParameterIn.PATH) @PathVariable("userId") Long userId
    ){
        UserDto processeduser = userService.deleteUser(userId);  
        return new ResponseEntity<User>(processeduser.toEntity(),HttpStatus.OK);
    }
    /**
     * Services the getUser API GET call.
     * @return The User object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<User> getUser(@Parameter(name = "userId", description = "ID of user to return", required = true, in = ParameterIn.PATH) @PathVariable("userId") Long userId
    ){
        UserDto processeduser = userService.getUser(userId);  
        return new ResponseEntity<User>(processeduser.toEntity(),HttpStatus.OK);
    }
    /**
     * Services the getUserList API GET call.
     * @return The User object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<List<User>> getUserList(
        @RequestParam(value = "query_string", defaultValue = Constants.DEFAULT_SEARCH_CRITERIA, required = false) String queryString,
        @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
        @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
        @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        List<UserDto> userDtoList = userService.getUserList(queryString,pageNumber,pageSize,sortBy,sortDir);
        List<User> userList = new ArrayList<User>();
        for(UserDto userDto : userDtoList){
            userList.add(userDto.toEntity());
        }
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }
    /**
     * Services the updateUser API PUT call.
     * @return The User object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<User> updateUser(@Parameter(name = "User", description = "Update an existent user in the user", required = true) @Valid @RequestBody User user
    ){
        UserDto userdto = new UserDto(user);
        UserDto processeduser = userService.updateUser(userdto);  
        return new ResponseEntity<User>(processeduser.toEntity(),HttpStatus.OK);
    }
}
