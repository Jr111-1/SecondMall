package com.lqf.fleamarket.controller.param;

import lombok.Data;


@Data
public class OrderReq {
    private long id;
    private long buyerId;
    private long commodityId;
    private int num;
    private String note;
    private String reason;
}
