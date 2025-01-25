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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductBL productBL;

    @ApiOperation("This API is used to add product in the user's cart")
    @PostMapping("/addToCart")
    public ResponseEntity<BaseResponse> addToCart(Product product) {
        try {
            productBL.addToCart(product);
            return BaseResponseUtil.createSuccessBaseResponse();
        } catch (Exception e) {
            log.error("Error in addToCart", e);
            return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("This API is used to create or save product details")
    @PostMapping("/saveProduct")
    public ResponseEntity<BaseResponse> saveProduct(@RequestBody List<Product> productList) {
        try {
            productBL.saveProducts(productList);
            return BaseResponseUtil.createSuccessBaseResponse();
        } catch (Exception e) {
            log.error("Error in saveProduct", e);
            return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("This API is used to create or save product details")
    @GetMapping("/getAllProducts")
    public ResponseEntity<BaseResponse> getAllProducts() {
        try {
            return productBL.getAllProducts();
        } catch (Exception e) {
            log.error("Error in getAllProducts", e);
            return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
