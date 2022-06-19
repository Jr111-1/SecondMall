package com.lqf.fleamarket.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;
    private String name;
    private String biography;
    private String email;
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
    private String payContent;
    private String saveContent;
    private int sellerLvl;
    private float score;
    private int isLicense;

}
