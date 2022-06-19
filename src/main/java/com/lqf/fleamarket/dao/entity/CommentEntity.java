package com.lqf.fleamarket.dao.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "tbl_comment")
@Data
@DynamicUpdate
@DynamicInsert
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long commodityId;
    private long reviewerId;
    private int rating;
    private String photoUrl;
    private String comment;
    @Temporal(TemporalType.TIMESTAMP) //帮Java的Date类型进行格式化
    @CreationTimestamp  //让Hibernate在插入时针对注解的属性对应的日期类型创建默认值
    private Date createdOn;
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp  //让Hibernate在更新时时针对注解的属性对应的日期类型创建默认值
    private Date modifiedOn;

    @OneToMany(mappedBy = "commentEntity")
    private List<CommentRespEntity> commentRespList;
}
