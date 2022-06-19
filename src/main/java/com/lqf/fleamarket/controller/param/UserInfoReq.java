package com.lqf.fleamarket.controller.param;

import lombok.Data;

@Data
public class UserInfoReq {
    private long id;
    private String name;
    private String email;
    private String biography;
    private String password;
    private String city;
    private String sex;
    private String bankid;
    private String tel;
    private String xingming;
    private float money;
    private String saveContent;
    private String payContent;
    private int sellerLvl;
    private float score;
    private int isLicense;

}
