package com.lqf.fleamarket.dao.entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "tbl_car")
@Entity
@DynamicUpdate
@DynamicInsert
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long commodityId;
    private long buyerId;
    private long sellerId;
    private int num;

    @Column(columnDefinition = "tinyint(1) default 1 comment '0为无效，1为有效'")
    private int isEffective = 1;
    private int status;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdOn;
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date modifiedOn;

}
