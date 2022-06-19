package com.lqf.fleamarket.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private long id;
    private long buyerId;
    private long commodityId;
    private String commodityName;
    private String photoUrl;
    private String reason;
    private long sellerId;
    private float price;
    private int num;
    private String note;
    private int status;
    private Date createdOn;

}
