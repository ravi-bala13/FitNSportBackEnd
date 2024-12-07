package com.fitnsport.server.Business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnsport.server.dto.AccessToken;
import com.fitnsport.server.response.BaseResponse;
import com.fitnsport.server.response.CustomerBaseResponse;
import com.fitnsport.server.utils.BaseResponseUtil;

import com.fitnsport.server.utils.JwtTokenUtil;
import com.fitnsport.server.utils.PasswordUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.beans.factory.annotation.Autowired;

import com.fitnsport.server.database.dao.CustomerDao;
import com.fitnsport.server.database.entity.Customer;
import com.fitnsport.server.Requests.CustomerBaseRequest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class CustomerBL {
    @Autowired
    CustomerDao customerDao;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    PasswordUtil passwordUtil;

    @Autowired
    private HttpServletResponse response;
    @Async
    public ResponseEntity<BaseResponse> saveUserDetails(CustomerBaseRequest customerBaseRequest) {
        String hashedPassword = passwordUtil.hashPassword(customerBaseRequest.getPassword());

        Customer user = Customer.builder()
                .customerName(customerBaseRequest.getName())
                .customerEmail(customerBaseRequest.getEmail())
                .password(hashedPassword)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        customerDao.save(user);

        AccessToken accessToken = AccessToken.builder()
                                    .userName(user.getCustomerName())
                                    .userEmail(user.getCustomerEmail())
                                    .createdAt(new Date()).build();
        String accessTokenstring = jwtTokenUtil.generateToken(accessToken);
        CustomerBaseResponse customerBaseResponse = CustomerBaseResponse.builder()
                .customerName(user.getCustomerName())
                .accessToken(accessTokenstring)
                .customerName(user.getCustomerName())
                .build();
        return BaseResponseUtil.createSuccessBaseResponse(customerBaseResponse);
    }

    public ResponseEntity<BaseResponse> getAllUsers() {
        List<Customer> customers;
            customers = customerDao.findAll();

        if(CollectionUtils.isEmpty(customers))
            return BaseResponseUtil.createBaseResponse("No users found", HttpStatus.OK);

        return BaseResponseUtil.createSuccessBaseResponse(CustomerBaseResponse.builder()
                .customers(customers)
                .build());
    }

    public BaseResponse getUserDetail(CustomerBaseRequest customerBaseRequest) {
        Optional<Customer> customerOpt = customerDao.findByCustomerEmail(customerBaseRequest.getEmail());

        if(customerOpt.isPresent()){
            Customer customer = customerOpt.get();
            boolean isAuthenticated = passwordUtil.verifyPassword(customerBaseRequest.getPassword(), customer.getPassword());
            if (isAuthenticated) {

                AccessToken accessToken = AccessToken.builder()
                                            .userName(customer.getCustomerName())
                                            .userEmail(customer.getCustomerName())
                                            .createdAt(new Date()).build();
                String accessTokenstring = jwtTokenUtil.generateToken(accessToken);

                return BaseResponseUtil.createSuccessBaseResponseWithResults(
                            CustomerBaseResponse.builder()
                                .customerName(customer.getCustomerName())
                                .accessToken(accessTokenstring)
                                .build());
            } else {
                return BaseResponseUtil.createErrorBaseResponse("Invalid email or password");
            }
        }
        return BaseResponseUtil.createNoDataBaseResponse();
    }

    public ResponseEntity<BaseResponse> checkWhetherUserLoggedIn(HttpServletRequest request){
        String accessToken = request.getHeader("Access-Token");

        if(StringUtils.isEmpty(accessToken)){
            return BaseResponseUtil.createBaseResponse(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED);
        }

        AccessToken accessTokenObj = jwtTokenUtil.extractAccessToken(accessToken);
        if(jwtTokenUtil.isTokenExpired(accessTokenObj)){
            return BaseResponseUtil.createBaseResponse(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED);
        }
        accessTokenObj.setCreatedAt(new Date()); // renewal of token

        CustomerBaseResponse customerBaseResponse = CustomerBaseResponse.builder()
                .accessToken(jwtTokenUtil.generateToken(accessTokenObj))
                .customerName(accessTokenObj.getUserName())
                .build();

        return BaseResponseUtil.createSuccessBaseResponse(customerBaseResponse);
    }

    public boolean isUserExists(String emailId) {
        return customerDao.findByCustomerEmail(emailId).isPresent();
    }

}
