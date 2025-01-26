package com.fitnsport.server.database.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "wish_list")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WishList {

    @Id
    private String id;

    @Field("customer_id")
    private Integer customerId;

    private List<Product> items;

    @Field("created_at")
    private Date createdAt;

    @Field("updated_at")
    private Date updatedAt;
}
