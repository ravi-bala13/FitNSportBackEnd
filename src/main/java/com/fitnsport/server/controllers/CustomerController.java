package com.fitnsport.server.controllers;

import lombok.extern.log4j.Log4j2;
import io.swagger.annotations.ApiOperation;
import io.micrometer.common.util.StringUtils;
import com.fitnsport.server.Business.CustomerBL;
import com.fitnsport.server.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fitnsport.server.utils.BaseResponseUtil;
import com.fitnsport.server.Requests.CustomerBaseRequest;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    CustomerBL customerBL;

    @ApiOperation("This api is used to get user details from the db")
    @GetMapping("/get-customer")
    public ResponseEntity<BaseResponse> getUsers(){
        try {
            return customerBL.getAllUsers();
        }catch (Exception e){
            log.info("Error in addUser", e);
            return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("This api is used to add product in the user cart")
    @GetMapping("/addToCart")
    public BaseResponse addToCart(){
        try {
            return BaseResponseUtil.createSuccessBaseResponseWithResults(customerBL.getAllUsers());
        }catch (Exception e){
            log.info("Error in addUser", e);
            return BaseResponseUtil.createErrorBaseResponse(e.getMessage());
        }
    }
}
