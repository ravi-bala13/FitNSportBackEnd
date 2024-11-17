package com.fitnsport.server.database.entity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customer_details")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {

    @Id
    private String id;

    @Field("customer_id")
    private Integer customerId;

    @Field("customer_name")
    private String customerName;

    private String password;

    @Field("customer_address")
    private String customerAddress;

    @Field("customer_phone")
    private String customerPhone;

    @Field("customer_dob")
    private Date customerDob;

    @Field("customer_email")
    private String customerEmail;

    @Field("register_id")
    private Integer registerId;

    @Field("created_at")
    private Date createdAt;

    @Field("updated_at")
    private Date updatedAt;

}
