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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BaseResponse> registerUser(@RequestBody CustomerBaseRequest customerBaseRequest) {
        try {
            log.info("customerBaseRequest - {}", customerBaseRequest);
            if (StringUtils.isEmpty(customerBaseRequest.getName()) || StringUtils.isEmpty(customerBaseRequest.getEmail())) {
                return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse("Username or Email must not be empty"), HttpStatus.BAD_REQUEST);
            }

            // Check if user already exists
            if (customerBL.isUserExists(customerBaseRequest.getEmail())) {
                return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse("User already exists"), HttpStatus.BAD_REQUEST);
            }

            return customerBL.saveUserDetails(customerBaseRequest);
        } catch (Exception e) {
            log.error("Error in registerUser", e);
            return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("This API is used to login a user")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse> loginUser(@RequestBody CustomerBaseRequest customerBaseRequest) {
        try {
            log.info("loginRequest - {}", customerBaseRequest);
            if (StringUtils.isEmpty(customerBaseRequest.getEmail()) || StringUtils.isEmpty(customerBaseRequest.getPassword())) {
                return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse("Email or Password must not be empty"), HttpStatus.BAD_REQUEST);
            }

            return ResponseEntity.ok(customerBL.getUserDetail(customerBaseRequest));
        } catch (Exception e) {
            log.error("Error in loginUser", e);
            return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("This API is used to login a user")
    @PostMapping("/checkWhetherUserLoggedIn")
    public ResponseEntity<BaseResponse> checkWhetherUserLoggedIn() {
        try {
            return customerBL.checkWhetherUserLoggedIn(request);
        } catch (Exception e) {
            log.error("Error in loginUser", e);
            return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
