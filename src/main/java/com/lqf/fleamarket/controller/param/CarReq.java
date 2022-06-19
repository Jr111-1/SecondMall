package com.lqf.fleamarket.controller.param;

import lombok.Data;
@Data
public class CarReq {
    private long buyerId;
    private long commodityId;
    private int num;
    private String note;
}
