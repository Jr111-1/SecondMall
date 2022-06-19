package com.lqf.fleamarket.controller.param;

import lombok.Data;


@Data
public class CommentReq {
    private long commodityId;
    private long reviewerId;
    private int rating;
    private String photoUrl;
    private String comment;
}
