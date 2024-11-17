package com.fitnsport.server.controllers;

import com.fitnsport.server.Business.CustomerBL;
import com.fitnsport.server.Requests.CustomerBaseRequest;
import com.fitnsport.server.response.BaseResponse;
import com.fitnsport.server.utils.BaseResponseUtil;
import io.micrometer.common.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    CustomerBL customerBL;
    @ApiOperation("This API is used to register a new user")
    @PostMapping("/signup")
    public BaseResponse registerUser(@RequestBody CustomerBaseRequest customerBaseRequest) {
        try {
            log.info("customerBaseRequest - {}", customerBaseRequest);
            if (StringUtils.isEmpty(customerBaseRequest.getName()) || StringUtils.isEmpty(customerBaseRequest.getEmail())) {
                return BaseResponseUtil.createErrorBaseResponse("Username or Email must not be empty");
            }

            // Check if user already exists
            if (customerBL.isUserExists(customerBaseRequest.getEmail())) {
                return BaseResponseUtil.createErrorBaseResponse("User already exists");
            }

            return customerBL.saveUserDetails(customerBaseRequest);
        } catch (Exception e) {
            log.error("Error in registerUser", e);
            return BaseResponseUtil.createErrorBaseResponse(e.getMessage());
        }
    }

    @ApiOperation("This API is used to login a user")
    @PostMapping("/login")
    public BaseResponse loginUser(@RequestBody CustomerBaseRequest customerBaseRequest) {
        try {
            log.info("loginRequest - {}", customerBaseRequest);
            if (StringUtils.isEmpty(customerBaseRequest.getEmail()) || StringUtils.isEmpty(customerBaseRequest.getPassword())) {
                return BaseResponseUtil.createErrorBaseResponse("Email or Password must not be empty");
            }

            return customerBL.getUserDetail(customerBaseRequest);
        } catch (Exception e) {
            log.error("Error in loginUser", e);
            return BaseResponseUtil.createErrorBaseResponse(e.getMessage());
        }
    }

    @ApiOperation("This API is used to login a user")
    @PostMapping("/checkWhetherUserLoggedIn")
    public BaseResponse checkWhetherUserLoggedIn() {
        try {
            return customerBL.checkWhetherUserLoggedIn(request);
        } catch (Exception e) {
            log.error("Error in loginUser", e);
            return BaseResponseUtil.createErrorBaseResponse(e.getMessage());
        }
    }
}
