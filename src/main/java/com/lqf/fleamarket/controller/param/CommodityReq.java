package com.lqf.fleamarket.controller.param;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class CommodityReq {
    private String name;
    private String description;
    private int quantity;
    private float price;
    private long ownerId;
    private MultipartFile photo;
    private int isselling;
    private String sellcontent;
}
