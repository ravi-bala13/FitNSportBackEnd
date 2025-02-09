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
    public ResponseEntity<BaseResponse> addToCart(@RequestBody Product product, @RequestParam(defaultValue = "false") boolean isUpdateCart) {
        try {
            productBL.addToCart(product, isUpdateCart);
            return BaseResponseUtil.createSuccessBaseResponse();
        } catch (Exception e) {
            log.error("Error in addToCart", e);
            return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("This API is used to remove product from the user's cart")
    @PostMapping("/removeFromCart/{productId}")
    public ResponseEntity<BaseResponse> removeFromCart(@PathVariable String productId) {
        try {
            productBL.removeFromCart(productId);
            return BaseResponseUtil.createSuccessBaseResponse();
        } catch (Exception e) {
            log.error("Error in removeFromCart", e);
            return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("This API is used to get user's cart details")
    @GetMapping("/getCartItems")
    public ResponseEntity<BaseResponse> getCartItems() {
        try {
            return productBL.getCartItems();
        } catch (Exception e) {
            log.error("Error in getCartItems", e);
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

    @ApiOperation("This API is used to get all product details")
    @GetMapping("/getAllProducts")
    public ResponseEntity<BaseResponse> getAllProducts() {
        try {
            return productBL.getAllProducts();
        } catch (Exception e) {
            log.error("Error in getAllProducts", e);
            return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("This API is used to get a product details for given productId")
    @GetMapping("/getProductDetails/{productId}")
    public ResponseEntity<BaseResponse> getProductDetails(@PathVariable String productId) {
        try {
            return productBL.getProductDetails(productId);
        } catch (Exception e) {
            log.error("Error in getProductDetails", e);
            return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("This API is used to add product in the user's wishList")
    @PostMapping("/addToWishList")
    public ResponseEntity<BaseResponse> addToWishList(@RequestBody Product product) {
        try {
            productBL.addToWishList(product);
            return BaseResponseUtil.createSuccessBaseResponse();
        } catch (Exception e) {
            log.error("Error in addToWishList", e);
            return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("This API is used to get user's wishlist items")
    @GetMapping("/getWishListItems")
    public ResponseEntity<BaseResponse> getWishListItems() {
        try {
            return productBL.getWishListItems();
        } catch (Exception e) {
            log.error("Error in getWishListItems", e);
            return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("This API is used to get TopSellingProducts")
    @GetMapping("/getTopSellingProducts")
    public ResponseEntity<BaseResponse> getTopSellingProducts() {
        try {
            return productBL.getTopSellingProducts();
        } catch (Exception e) {
            log.error("Error in getTopSellingProducts", e);
            return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("This API is used to get RelatedProducts for given product")
    @GetMapping("/getRelatedProducts")
    public ResponseEntity<BaseResponse> getRelatedProducts() {
        try {
            return productBL.getTopSellingProducts();
        } catch (Exception e) {
            log.error("Error in getRelatedProducts", e);
            return new ResponseEntity<>(BaseResponseUtil.createErrorBaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
