package com.fitnsport.server.Business;

import com.fitnsport.server.Components.AccessTokenParserHelper;
import com.fitnsport.server.database.dao.CartDao;
import com.fitnsport.server.database.entity.Cart;
import com.fitnsport.server.database.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class ProductBL {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private AccessTokenParserHelper accessTokenParserHelper;

    public void addToCart(Product product){
        Optional<Cart> optionalCart = cartDao.findByCartUserId(accessTokenParserHelper.accessTokenObj.getUserId());

        Cart cartDetails;
        if (optionalCart.isPresent()) {
            cartDetails = optionalCart.get();

            // Check if product already exists in the cart
            Optional<Product> existingProduct = cartDetails.getItems().stream()
                    .filter(item -> item.getProductId().equals(product.getProductId()))
                    .findFirst();

            if (existingProduct.isPresent()) {
                // Update quantity or other fields if needed
                existingProduct.get().setQuantity(existingProduct.get().getQuantity() + product.getQuantity());
                cartDetails.setTotalPrice(cartDetails.getTotalPrice() + product.getPrice());
            } else {
                cartDetails.getItems().add(product);
            }

        } else {
            cartDetails = Cart.builder()
                    .customerId(accessTokenParserHelper.accessTokenObj.getUserId())
                    .items(Collections.singletonList(product))
                    .build();
        }
        cartDao.save(cartDetails);
    }
}
