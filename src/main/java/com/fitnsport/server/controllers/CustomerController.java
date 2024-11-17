package com.fitnsport.server.controllers;

import lombok.extern.log4j.Log4j2;
import io.swagger.annotations.ApiOperation;
import io.micrometer.common.util.StringUtils;
import com.fitnsport.server.Business.CustomerBL;
import com.fitnsport.server.response.BaseResponse;
import org.springframework.web.bind.annotation.*;
import com.fitnsport.server.utils.BaseResponseUtil;
import com.fitnsport.server.Requests.CustomerBaseRequest;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerBL customerBL;

    @ApiOperation("This api is used to add user in the db")
    @PostMapping("/add-customer")
    public BaseResponse addUser(@RequestBody CustomerBaseRequest customerBaseRequest){
        try {
            log.info("centralBaseRequest - {}", customerBaseRequest);
            if(StringUtils.isEmpty(customerBaseRequest.getName()) || StringUtils.isEmpty(customerBaseRequest.getEmail()))
                return BaseResponseUtil.createErrorBaseResponse("Username or Email must not be empty");

            if (customerBL.isUserExists(customerBaseRequest.getEmail())) {
                return BaseResponseUtil.createErrorBaseResponse("User already exists");
            }

            customerBL.saveUserDetails(customerBaseRequest);
            return BaseResponseUtil.createSuccessBaseResponse();
        }catch (Exception e){
            log.info("Error in addUser", e);
            return BaseResponseUtil.createErrorBaseResponse(e.getMessage());
        }
    }

    @ApiOperation("This api is used to get user details from the db")
    @GetMapping("/get-customer")
    public BaseResponse getUsers(){
        try {
            return BaseResponseUtil.createSuccessBaseResponseWithResults(customerBL.getUserDetails());
        }catch (Exception e){
            log.info("Error in addUser", e);
            return BaseResponseUtil.createErrorBaseResponse(e.getMessage());
        }
    }
}
