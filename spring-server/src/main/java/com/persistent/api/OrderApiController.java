/**
 * Controller class for handling CRUD operations on Order resource.
 */
package com.persistent.api;
import com.persistent.model.Order;

import com.persistent.model.Order;
import com.persistent.model.OrderDto;
import com.persistent.service.OrderService;



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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-11-28T04:24:25.988Z[UTC]")
@Controller
@RequestMapping("${openapi.swaggerPetstoreOpenAPI30.base-path:/api/v3}")
public class OrderApiController implements OrderApi {

    private final NativeWebRequest request;

    /**
     * Service instance for database operations.
     */
    @Autowired
    private OrderService orderService;

    @Autowired
    public OrderApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    /**
     * Services the addOrder API POST call.
     * @return The Order object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<Order> addOrder(@Parameter(name = "Order", description = "Create a new order in the store", required = true) @Valid @RequestBody Order order
    ){
        OrderDto orderdto = new OrderDto(order);
        OrderDto processedorder = orderService.addOrder(orderdto);  
        return new ResponseEntity<Order>(processedorder.toEntity(),HttpStatus.OK);
    }
    /**
     * Services the deleteOrder API DELETE call.
     * @return The Order object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<Order> deleteOrder(@Parameter(name = "orderId", description = "Order id to delete", required = true, in = ParameterIn.PATH) @PathVariable("orderId") Long orderId
    ){
        OrderDto processedorder = orderService.deleteOrder(orderId);  
        return new ResponseEntity<Order>(processedorder.toEntity(),HttpStatus.OK);
    }
    /**
     * Services the getOrder API GET call.
     * @return The Order object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<Order> getOrder(@Parameter(name = "orderId", description = "ID of order to return", required = true, in = ParameterIn.PATH) @PathVariable("orderId") Long orderId
    ){
        OrderDto processedorder = orderService.getOrder(orderId);  
        return new ResponseEntity<Order>(processedorder.toEntity(),HttpStatus.OK);
    }
    /**
     * Services the getOrderList API GET call.
     * @return The Order object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<List<Order>> getOrderList(
        @RequestParam(value = "query_string", defaultValue = Constants.DEFAULT_SEARCH_CRITERIA, required = false) String queryString,
        @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
        @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
        @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        List<OrderDto> orderDtoList = orderService.getOrderList(queryString,pageNumber,pageSize,sortBy,sortDir);
        List<Order> orderList = new ArrayList<Order>();
        for(OrderDto orderDto : orderDtoList){
            orderList.add(orderDto.toEntity());
        }
        return new ResponseEntity<List<Order>>(orderList, HttpStatus.OK);
    }
    /**
     * Services the updateOrder API PUT call.
     * @return The Order object with the API call response and appropriate HTTP 
     *         Status code.
     */
    @Override
    public ResponseEntity<Order> updateOrder(@Parameter(name = "Order", description = "Update an existent order in the store", required = true) @Valid @RequestBody Order order
    ){
        OrderDto orderdto = new OrderDto(order);
        OrderDto processedorder = orderService.updateOrder(orderdto);  
        return new ResponseEntity<Order>(processedorder.toEntity(),HttpStatus.OK);
    }
}
