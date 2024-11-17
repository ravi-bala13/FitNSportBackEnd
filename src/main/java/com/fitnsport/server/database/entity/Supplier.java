package com.fitnsport.server.database.entity;

import lombok.Data;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document(collection = "supplier_details")
public class Supplier {
    @Id
    @Field("supplier_id")
    private Integer supplierId;

    @Field("supplier_name")
    private String supplierName;

    @Field("supplierAddress")
    private String supplier_address;

    private String dispatch;

    @Field("supplier_phone")
    private Integer supplierPhone;

    @Field("category_id")
    private Integer categoryId;

    @Field("initial_cost")
    private Double initialCost;

    private Double percentage;

    @Field("created_at")
    private Date createdAt;

    @Field("updated_at")
    private Date updatedAt;
}
