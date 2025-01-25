package com.fitnsport.server.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "product_details")
public class Product {

    @Id
    @Field("product_id")
    private String productId;

    @Field("product_name")
    private String productName;

    @Field("product_description")
    private String productDescription;

    @Field("image_url")
    private String imageUrl;

    @Field("height")
    private String height;

    @Field("width")
    private String width;

    @Field("weight")
    private String weight;

    @Field("supplier_id")
    private Integer supplierId;

    @Field("category_id")
    private Integer categoryId;

    private Double price;

    private Double rating;

    private Integer quantity;

    @Field("created_at")
    private Date createdAt;

    @Field("updated_at")
    private Date updatedAt;

}
