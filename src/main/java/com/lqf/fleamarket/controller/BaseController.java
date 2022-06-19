package com.lqf.fleamarket.controller;

import com.lqf.fleamarket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/api")
public class BaseController {
    @Autowired
    UserService userService;
    @Autowired
    CommodityService commodityService;
    @Autowired
    OrderService orderService;
    @Autowired
    CommentService commentService;
    @Autowired
    CarService carService;
}
