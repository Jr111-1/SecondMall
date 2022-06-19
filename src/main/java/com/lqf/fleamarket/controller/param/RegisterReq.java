package com.lqf.fleamarket.controller.param;

import lombok.Data;


@Data
public class RegisterReq {
    private String name;
    private String email;
    private String password;
    private String city;
    private String sex;
    private String bankid;
    private String tel;
    private String xingming;
}
