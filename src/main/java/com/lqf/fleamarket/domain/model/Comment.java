package com.lqf.fleamarket.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private long id;
    private long commodityId;
    private long reviewerId;
    private String reviewerName;
    private int rating;
    private String photoUrl;
    private String comment;
    private Date createdOn;
}
