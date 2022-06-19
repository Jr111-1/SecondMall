package com.lqf.fleamarket.controller.param;

import lombok.Data;


@Data
public class CommentRespReq {
    private long commentId;
    private long reviewerId;
    private long replierId;
    private long parentId;
    private String comment;
}
