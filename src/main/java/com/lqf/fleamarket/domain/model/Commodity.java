package com.lqf.fleamarket.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commodity {
    private long id;
    private String name;
    private String description;
    private String photoUrl;
    private int quantity;
    private float price;
    private long ownerId;
    private int status;
    private int isselling;
    private String sellcontent;
    private int sellnum;

}
