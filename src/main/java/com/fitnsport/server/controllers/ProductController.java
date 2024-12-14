package com.fitnsport.server.controllers;

import com.fitnsport.server.Business.ProductBL;
import com.fitnsport.server.database.entity.Product;
import com.fitnsport.server.response.BaseResponse;
import com.fitnsport.server.utils.BaseResponseUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductBL productBL;

    @ApiOperation("This API is used to add product in the user's cart")
    @PostMapping("/api/add-to-cart")
    public ResponseEntity<BaseResponse> addToCart(Product product) {
        try {
            productBL.addToCart(product);
            return BaseResponseUtil.createSuccessBaseResponse();
        } catch (Exception e) {
            log.error("Error in loginUser", e);
            return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
