package com.lqf.fleamarket.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private long id;
    private long buyerId;
    private String commodityName;
    private String photoUrl;
    private long sellerId;
    private float price;
    private int num;
    private String note;
    private int status;
    private Date createdOn;
}
