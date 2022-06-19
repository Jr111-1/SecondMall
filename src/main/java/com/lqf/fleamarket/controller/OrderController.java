package com.lqf.fleamarket.controller;

import com.lqf.fleamarket.controller.param.CarReq;
import com.lqf.fleamarket.controller.param.OrderReq;
import com.lqf.fleamarket.domain.model.Order;
import com.lqf.fleamarket.domain.pojo.ResponseCode;
import com.lqf.fleamarket.domain.pojo.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class OrderController extends BaseController {

    /**
     * 下订单
     * @param orderReq
     * @return
     */
    @PostMapping("/orders")
    public ResponseData createOrder(@RequestBody OrderReq orderReq) {
        Order order = this.orderService.createOrder(orderReq);
        if (order != null) {
            return new ResponseData(ResponseCode.SUCCSEE, order);
        }
        return new ResponseData(ResponseCode.BAD_REQUEST);
    }

    @PostMapping("/orders/")
    public ResponseData createOrder2(@RequestBody OrderReq orderReq){
        Order order = this.orderService.createOrder2(orderReq);
        if (order != null) {
            return new ResponseData(ResponseCode.SUCCSEE, order);
        }
        return new ResponseData(ResponseCode.BAD_REQUEST);
    }


    /**
     * 修改订单
     * @param id
     * @param note
     * @return
     */
    @PutMapping("/orders/{id}")
    public ResponseData updateOrder(@PathVariable long id, @RequestParam("note") String note) {
        Order order = this.orderService.updateOrder(id, note);
        if (order == null) {
            return new ResponseData(ResponseCode.BAD_REQUEST);
        }
        return new ResponseData(ResponseCode.SUCCSEE, order);
    }

    /**
     * 删除订单
     * @param id
     * @return
     */
    @DeleteMapping("/orders/{id}")
    public ResponseData deleteOrder(@PathVariable("id") long id) {
        this.orderService.deleteOrder(id);
        return new ResponseData(ResponseCode.SUCCSEE);
    }

    /**
     * 根据买家id获得订单，即已购买的商品
     * @param buyerId
     * @return
     */
    @GetMapping("/buyer-orders/{buyerId}")
    public ResponseData getAllByBuyerId(@PathVariable long buyerId) {
        return new ResponseData(ResponseCode.SUCCSEE, this.orderService.getAllByBuyerId(buyerId));
    }

    /**
     * 用户的订单管理，即卖出去的商品的订单
     * @param sellerId
     * @return
     */
    @GetMapping("/pending-orders/{sellerId}")
    public ResponseData getAllBySellerId(@PathVariable long sellerId) {
        return new ResponseData(ResponseCode.SUCCSEE, this.orderService.getAllBySellerId(sellerId));
    }
    //获取购物车中的商品
    @GetMapping("/shopcar-orders/{buyerId}")
    public ResponseData getAllByStatus(@PathVariable long buyerId) {
        return new ResponseData(ResponseCode.SUCCSEE, this.orderService.getAllByStatus(buyerId));
    }

    /**
     * 改变订单的状态
     * @param id
     * @param status
     * @return
     */
    @PutMapping("/pending-orders/{id}")
    public ResponseData changeStatus(@PathVariable long id, @RequestParam int status) {
        if (this.orderService.updateStatus(id, status)) {
            log.info(status + "");
            return new ResponseData(ResponseCode.SUCCSEE, status);
        } else {
            return new ResponseData(ResponseCode.BAD_REQUEST);
        }
    }
    //退款
    @PutMapping("/pending-orders/return/{id}")
    public ResponseData returnMoney(@PathVariable long id) {
        if (this.orderService.returnMoney(id)) {
            return new ResponseData(ResponseCode.SUCCSEE);
        }
        else {
            return new ResponseData(ResponseCode.BAD_REQUEST);
        }
    }
    //商家收款
    @PutMapping("/pending-orders/get/{id}")
    public ResponseData getMoney(@PathVariable long id) {
        if (this.orderService.getMoney(id)) {
            return new ResponseData(ResponseCode.SUCCSEE);
        }
        else {
            return new ResponseData(ResponseCode.BAD_REQUEST);
        }
    }
    //拒绝原因
    @PutMapping("/pending-orders/returnReason/")
    public ResponseData returnReason(@RequestBody OrderReq orderReq) {
        return new ResponseData(ResponseCode.SUCCSEE,this.orderService.getReason(orderReq));
    }
    //获取当前id订单信息
    @GetMapping("/pending-orders/getReason/{id}")
    public ResponseData getReason(@PathVariable long id) {
        return new ResponseData(ResponseCode.SUCCSEE,this.orderService.getOrderById(id));
    }
}
