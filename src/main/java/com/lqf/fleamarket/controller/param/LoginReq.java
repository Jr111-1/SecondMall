package com.lqf.fleamarket.controller.param;

import lombok.Data;


@Data
public class LoginReq {
    private String email;
    private String password;
    private int role;
}
