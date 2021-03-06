package com.lqf.fleamarket.domain;

import com.lqf.fleamarket.domain.model.CommentResp;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class CommentDTO {
    private long id;
    private long reviewerId;
    private String reviewerName;
    private int rating;
    private String photoUrl;
    private String comment;
    private Date createdOn;
    private List<CommentResp> commentRespList;
}
