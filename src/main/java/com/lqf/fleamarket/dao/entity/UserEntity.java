package com.lqf.fleamarket.dao.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name = "tbl_user")
@DynamicUpdate
@DynamicInsert
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String biography;
    private String email;
    private String password;
    private int role;
    private String photoUrl;
    private String license_photo;
    private String idPhoto;
    private String sex;
    private String city;
    private String bankid;
    private String tel;
    private String xingming;
    private float money;
    private float score;
    private String payContent;
    private String saveContent;

    @Column(columnDefinition = "tinyint(1) default -1")
    private int isLicense;

    @Column(columnDefinition = "tinyint(1) default 5")
    private int sellerLvl;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdOn;
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date modifiedOn;
}
