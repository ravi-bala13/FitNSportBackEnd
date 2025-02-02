package com.fitnsport.server.Business;

import com.fitnsport.server.Components.AccessTokenParserHelper;
import com.fitnsport.server.database.dao.CartDao;
import com.fitnsport.server.database.dao.ProductDao;
import com.fitnsport.server.database.dao.WishListDao;
import com.fitnsport.server.database.entity.Cart;
import com.fitnsport.server.database.entity.Product;
import com.fitnsport.server.database.entity.WishList;
import com.fitnsport.server.response.BaseResponse;
import com.fitnsport.server.response.CustomerBaseResponse;
import com.fitnsport.server.utils.BaseResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductBL {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private WishListDao wishListDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private AccessTokenParserHelper accessTokenParserHelper;

    public void addToCart(Product product){
        Optional<Cart> optionalUserCart = cartDao.findByCustomerId(accessTokenParserHelper.accessTokenObj.getUserId());

        Cart cartDetails;
        if (optionalUserCart.isPresent()) {
            cartDetails = optionalUserCart.get();

            // Check if product already exists in the cart
            Optional<Product> existingProduct = cartDetails.getItems().stream()
                    .filter(item -> item.getId().equals(product.getId()))
                    .findFirst();

            if (existingProduct.isPresent()) {
                // Update quantity or other fields if needed
                existingProduct.get().setQuantity(existingProduct.get().getQuantity() + product.getQuantity());
                existingProduct.get().setPrice(existingProduct.get().getPrice() + product.getPrice());
                cartDetails.setTotalPrice(cartDetails.getTotalPrice() + product.getPrice());
                cartDetails.setUpdatedAt(new Date());
            } else {
                cartDetails.getItems().add(product);
                cartDetails.setTotalPrice(cartDetails.getTotalPrice() + product.getPrice());
                cartDetails.setUpdatedAt(new Date());
            }

        } else {
            cartDetails = Cart.builder()
                    .customerId(accessTokenParserHelper.accessTokenObj.getUserId())
                    .items(Collections.singletonList(product))
                    .totalPrice(product.getPrice())
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();
        }
        cartDao.save(cartDetails);
    }

    public void addToWishList(Product product){
        Optional<WishList> optionalUserWishList = wishListDao.findByCustomerId(accessTokenParserHelper.accessTokenObj.getUserId());

        WishList wishList;
        if (optionalUserWishList.isPresent()) {
            wishList = optionalUserWishList.get();

            // Check if product already exists in the cart
            Optional<Product> existingProduct = wishList.getItems().stream()
                    .filter(item -> item.getId().equals(product.getId()))
                    .findFirst();

            if (existingProduct.isPresent()) {
                //ignore do nothing
                return;
            } else {
                wishList.getItems().add(product);
                wishList.setUpdatedAt(new Date());
            }

        } else {
            wishList = WishList.builder()
                    .customerId(accessTokenParserHelper.accessTokenObj.getUserId())
                    .items(Collections.singletonList(product))
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();
        }
        wishListDao.save(wishList);
    }

    public void removeFromCart(String productId) {
        Optional<Cart> optionalUserCart = cartDao.findByCustomerId(accessTokenParserHelper.accessTokenObj.getUserId());

        if (optionalUserCart.isPresent()) {
            Cart cartDetails = optionalUserCart.get();

            // Find the product in the cart
            Optional<Product> existingProduct = cartDetails.getItems().stream()
                    .filter(item -> item.getId().equals(productId))
                    .findFirst();

            if (existingProduct.isPresent()) {
                Product product = existingProduct.get();

                if (product.getQuantity() > 1) {
                    // 🔹 Decrease quantity and price if more than 1
                    product.setQuantity(product.getQuantity() - 1);
                    product.setPrice(product.getPrice()/2);
                    cartDetails.setTotalPrice(cartDetails.getTotalPrice() - product.getPrice());
                } else {
                    // 🔹 Remove product from cart if quantity is 1
                    cartDetails.getItems().remove(product);
                }
                cartDao.save(cartDetails);
            }
        }
    }

    public ResponseEntity<BaseResponse> getCartItems(){
        Optional<Cart> optionalUserCart = cartDao.findByCustomerId(accessTokenParserHelper.accessTokenObj.getUserId());
        return BaseResponseUtil.createSuccessBaseResponse(optionalUserCart.orElseGet(Cart::new));
    }

    public ResponseEntity<BaseResponse> getWishListItems(){
        Optional<WishList> optionalUserCart = wishListDao.findByCustomerId(accessTokenParserHelper.accessTokenObj.getUserId());
        return BaseResponseUtil.createSuccessBaseResponse(optionalUserCart.orElseGet(WishList::new));
    }

    public ResponseEntity<BaseResponse> getTopSellingProducts(){
        List<Product> topRatedProducts = productDao.findTopRatedProducts();
        return BaseResponseUtil.createSuccessBaseResponse(topRatedProducts);
    }

    public void saveProducts(List<Product> productList){
        for (int i = 0; i < productList.size(); i++) {
            productList.get(i).setProductId(generateProductId(productList.get(i), i + 1));
        }

        productDao.saveAll(productList);
    }

    private String generateProductId(Product product, int inc) {
        // Get the count directly from the database (faster & avoids large fetches)
        long count = productDao.countByProductTypeEnum(product.getProductTypeEnum()) + inc;

        // Generate ID in the format "bat_0000001"
        return String.format("%s_%07d", product.getProductTypeEnum().toString().toLowerCase(), count);
    }


    public ResponseEntity<BaseResponse> getAllProducts(){
        List<Product> productList = productDao.findAll();
        return BaseResponseUtil.createSuccessBaseResponse(productList);
    }

    public ResponseEntity<BaseResponse> getProductDetails(String productId){
        Optional<Product> optionalProduct = productDao.findById(productId);
        return optionalProduct.map(BaseResponseUtil::createSuccessBaseResponse).orElseGet(BaseResponseUtil::createNoDataBaseResponse);

    }
}
