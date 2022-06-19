package com.lqf.fleamarket.service.impl;

import com.lqf.fleamarket.domain.model.Commodity;
import com.lqf.fleamarket.service.CommodityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Description detail
 * Created by lqf on 2021/5/23 10:23
 */
@SpringBootTest
public class CommodityServ {
    @Autowired
    CommodityService commodityService;

    @Test
    public void test() {
        List<Commodity> entities = this.commodityService.getCommoditiesByStatus(CommodityService.STATUS_COMMON, 16, 1);
        System.err.println(entities.isEmpty());
    }
}
