package com.lqf.fleamarket.views;

import com.lqf.fleamarket.domain.model.Commodity;
import lombok.Data;

import java.util.List;

/**
 * @Description detail
 * Created by lqf on 2021/5/14 16:25
 */
@Data
public class HomeVO {
    private List<Commodity> bannerList;
    private List<Commodity> hotList;
    private List<Commodity> commonList;
}
