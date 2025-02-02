package com.fitnsport.server.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fitnsport.server.Enums.ProductTypeEnum;
import com.fitnsport.server.dto.Specifications;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "product_details")
public class Product {

    @Id
    private String id;

    @Field("product_id")
    private String productId;

    @Field("product_name")
    private String productName;

    @Field("precise_description")
    private String preciseDescription;

    @Field("enhanced_description")
    private String enhancedDescription;

    private List<Specifications> specifications;

    @Field("product_type")
    private ProductTypeEnum productTypeEnum;

    @Field("image_url")
    private String imageUrl;

    private String height;

    private String width;

    private String weight;

    private String size;

    @Field("supplier_id")
    private Integer supplierId;

    @Field("category_id")
    private Integer categoryId;

    private Double price = 0.0;

    private Double rating = 0.0;

    private Integer quantity = 1;

    @Field("created_at")
    private Date createdAt;

    @Field("updated_at")
    private Date updatedAt;
}
