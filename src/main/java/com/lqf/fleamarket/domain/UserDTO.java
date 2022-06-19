package com.lqf.fleamarket.domain;

import lombok.Data;

import java.util.Date;


@Data
public class UserDTO {
    private long id;
    private String name;
    private Date createdOn;
    private String biography;
    private String email;
    private int role;
    private String photoUrl;
    private String sex;
    private String city;
    private String bankid;
    private String tel;
    private String xingming;
    private float money;
    private int sellerLvl;
    private int isLicense;
    private String license_photo;
    private String idPhoto;
}
