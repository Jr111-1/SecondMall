package com.lqf.fleamarket.service;

import com.lqf.fleamarket.controller.param.CarReq;
import com.lqf.fleamarket.controller.param.OrderReq;
import com.lqf.fleamarket.domain.model.Order;

import java.util.List;


public interface OrderService {
    Order createOrder(OrderReq orderReq);
    Order createOrder2(OrderReq orderReq);
    Order updateOrder(long id, String note);
    boolean updateStatus(long id, int status);
    void deleteOrder(long id);
    boolean returnMoney(long id);//退钱
    boolean getMoney(long id);//商家收款
    List<Order> getAllByBuyerId(long buyerId);
    List<Order> getAllBySellerId(long sellerId);
    List<Order> getAllByStatus(long buyerId);
    Order buyFromCar(CarReq carReq);
    Order getReason(OrderReq orderReq);
    Order getOrderById(long id);
}
