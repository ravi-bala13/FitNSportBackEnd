package com.fitnsport.server.database.entity;

import lombok.Data;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document(collection = "product_details")
public class Product {

    @Id
    @Field("item_id")
    private Integer itemId;

    @Field("item_name")
    private String itemName;

    @Field("item_description")
    private String itemDescription;

    @Field("supplier_id")
    private Integer supplierId;

    @Field("category_id")
    private Integer categoryId;

    private Double price;

    @Field("created_at")
    private Date createdAt;

    @Field("updated_at")
    private Date updatedAt;

}
