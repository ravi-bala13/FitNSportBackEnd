package com.fitnsport.server.Business;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.ResponseCookie;
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
    public BaseResponse saveUserDetails(CustomerBaseRequest customerBaseRequest) {
        String hashedPassword = passwordUtil.hashPassword(customerBaseRequest.getPassword());

        Customer user = Customer.builder()
                .customerName(customerBaseRequest.getName())
                .customerEmail(customerBaseRequest.getEmail())
                .password(hashedPassword)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        customerDao.save(user);

        return BaseResponseUtil.createSuccessBaseResponseWithResults(
                CustomerBaseResponse.builder()
                        .customerName(user.getCustomerName())
                        .accessToken(jwtTokenUtil.generateToken(user))
                        .build());
    }

    private void setAccessTokenInCookie(Customer user) {
        String token = jwtTokenUtil.generateToken(user);

//        Cookie cookie = new Cookie("access-token", token);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
//        cookie.setPath("/");
//        cookie.setDomain("localhost");
//        cookie.setMaxAge(3600);    // Set the cookie expiration time (1 hour)
//        response.addCookie(cookie);

        ResponseCookie resCookie = ResponseCookie.from("access-token", token)
                .httpOnly(true)
                .sameSite("None")
                .path("/")
                .maxAge(3600)
                .build();
        response.addHeader("Set-Cookie", resCookie.toString());
    }

    public BaseResponse getUserDetails() {
        try {
            List<Customer> customers;
                customers = customerDao.findAll();

            if(CollectionUtils.isEmpty(customers))
                return BaseResponseUtil.createNoDataBaseResponse();

            return CustomerBaseResponse.builder()
                    .customers(customers)
                    .es(0)
                    .message("success !")
                    .status(200)
                    .time(System.currentTimeMillis())
                    .build();
        }catch (Exception e){
            log.info("Error in getUserDetails", e);
            return BaseResponseUtil.createErrorBaseResponse();
        }
    }

    public BaseResponse getUserDetail(CustomerBaseRequest customerBaseRequest) {
        try {
            Optional<Customer> customerOpt = customerDao.findByCustomerEmail(customerBaseRequest.getEmail());

            if(customerOpt.isPresent()){
                Customer customer = customerOpt.get();
                boolean isAuthenticated = passwordUtil.verifyPassword(customerBaseRequest.getPassword(), customer.getPassword());
                if (isAuthenticated) {
                    return BaseResponseUtil.createSuccessBaseResponseWithResults(
                                CustomerBaseResponse.builder()
                                    .customerName(customer.getCustomerName())
                                    .accessToken(jwtTokenUtil.generateToken(customer))
                                    .build());
                } else {
                    return BaseResponseUtil.createErrorBaseResponse("Invalid email or password");
                }
            }
            return BaseResponseUtil.createNoDataBaseResponse();
        }catch (Exception e){
            log.info("Error in getUserDetails", e);
            return BaseResponseUtil.createErrorBaseResponse();
        }
    }

    public BaseResponse checkWhetherUserLoggedIn(HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        String accessToken = request.getHeader("Access-Token");

        if(StringUtils.isEmpty(accessToken)){
            baseResponse.setMessage("Not authorized");
            return  baseResponse;
        }

        return BaseResponseUtil.createSuccessBaseResponseWithResults(
                CustomerBaseResponse.builder()
                        .accessToken(jwtTokenUtil.generateToken(new Customer()))
                        .build());

//        ObjectMapper objectMapper = new ObjectMapper();
//        Customer user;
//        try {
//            user = objectMapper.readValue(accessToken, Customer.class);
//        }catch (Exception e){
//            baseResponse.setMessage("Invalid AccessToken");
//            return  baseResponse;
//        }
//        if (!isUserExists(user.getCustomerEmail())) {
//            baseResponse.setMessage("User not found");
//            return baseResponse;
//        }
//        return BaseResponseUtil.createSuccessBaseResponseWithResults(user);
    }

    public boolean isUserExists(String emailId) {
        return customerDao.findByCustomerEmail(emailId).isPresent();
    }

    public boolean authenticateUser(String emailId, String password) {
        Optional<Customer> customerOpt = customerDao.findByCustomerEmail(emailId);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            return passwordUtil.verifyPassword(password, customer.getPassword());
        }
        return false;
    }
}
