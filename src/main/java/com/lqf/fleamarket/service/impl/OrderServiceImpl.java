package com.lqf.fleamarket.service.impl;

import com.lqf.fleamarket.controller.param.CarReq;
import com.lqf.fleamarket.controller.param.OrderReq;
import com.lqf.fleamarket.dao.entity.CommodityEntity;
import com.lqf.fleamarket.dao.entity.OrderEntity;
import com.lqf.fleamarket.dao.entity.UserEntity;
import com.lqf.fleamarket.dao.repo.CommodityRepository;
import com.lqf.fleamarket.dao.repo.OrderRepository;
import com.lqf.fleamarket.dao.repo.UserRepository;
import com.lqf.fleamarket.domain.model.Order;
import com.lqf.fleamarket.domain.model.User;
import com.lqf.fleamarket.service.CommodityService;
import com.lqf.fleamarket.service.OrderService;
import com.lqf.fleamarket.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Override
    public Order createOrder(OrderReq orderReq) {
        long commodityId = orderReq.getCommodityId();
        long buyerId = orderReq.getBuyerId();
        int num = orderReq.getNum();
        float pay = commodityService.getPrice(commodityId) * num;

        UserEntity userEntity = this.userRepository.getOne(buyerId);
        float money = userEntity.getMoney();

        long canBuy = this.userService.updataMoney(buyerId,pay);
        if(canBuy != -1){
            long isSuccessful = this.commodityService.updateQuantity(commodityId, num);  // 查询商品剩余数量是否够
            if (isSuccessful != -1) {
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setSellerId(isSuccessful);
                this.userService.payMoney(buyerId,pay);
//                this.userService.updataScore(buyerId,pay);
                this.commodityService.updateSellnum(commodityId,num);
                return save(orderReq, orderEntity);
            }
        }

        return null;
    }
    //加入购物车
    @Override
    public Order createOrder2(OrderReq orderReq) {
        long commodityId = orderReq.getCommodityId();
        long buyerId = orderReq.getBuyerId();
        int num = orderReq.getNum();
        float pay = commodityService.getPrice(commodityId) * num;

        UserEntity userEntity = this.userRepository.getOne(buyerId);
        float money = userEntity.getMoney();

        long isSuccessful = this.commodityService.updateQuantity2(commodityId, num);  // 查询商品剩余数量是否够
        if (isSuccessful != -1) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setSellerId(isSuccessful);
            orderEntity.setStatus(4);
            this.userService.payMoney(buyerId,pay);
            return save(orderReq, orderEntity);
        }
        return null;
    }

    @Override
    public Order updateOrder(long id, String note) {
        try {
            OrderEntity orderEntity = this.orderRepository.getOne(id); //先通过id找到Order，否则创建时间会变为空
            orderEntity.setNote(note);
            this.orderRepository.save(orderEntity);
            Order order = new Order();
            BeanUtils.copyProperties(orderEntity, order);
            return order;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 内部方法，将OrderReq请求拷贝保存，并返回Order
     * @param orderReq
     * @param orderEntity
     * @return
     */
    private Order save(OrderReq orderReq, OrderEntity orderEntity) {
        BeanUtils.copyProperties(orderReq, orderEntity);
        this.orderRepository.save(orderEntity);
        Order order = new Order();
        BeanUtils.copyProperties(orderEntity, order);
        return order;
    }
    private Order save2(CarReq carReq, OrderEntity orderEntity) {
        BeanUtils.copyProperties(carReq, orderEntity);
        orderEntity.setStatus(0);
        this.orderRepository.save(orderEntity);
        Order order = new Order();
        BeanUtils.copyProperties(orderEntity, order);
        return order;
    }

    @Override
    public void deleteOrder(long id) {
        this.orderRepository.deleteById(id);
    }



    @Override
    public List<Order> getAllByBuyerId(long buyerId) {
        List<OrderEntity> entities = this.orderRepository.findAllByBuyerId(buyerId);
        List<Order> orders = entities.stream().map(entity -> {
            CommodityEntity commodityEntity = this.commodityRepository.getOne(entity.getCommodityId());
            Order order = new Order();
            BeanUtils.copyProperties(entity, order);
            order.setCommodityName(commodityEntity.getName());
            order.setPhotoUrl(commodityEntity.getPhotoUrl());
            order.setPrice(commodityEntity.getPrice());
            return order;
        }).collect(Collectors.toList());
        return orders;
    }

    /**
     * 根据卖家id获取订单，订单中的商品id需要转换成商品的名称, 还需添加商品的图片url
     * @param sellerId
     * @return
     */
    @Override
    public List<Order> getAllBySellerId(long sellerId) {
        List<OrderEntity> entities = this.orderRepository.findAllBySellerId(sellerId);
        List<Order> orders = entities.stream().map(entity -> {
            CommodityEntity commodityEntity = this.commodityRepository.getOne(entity.getCommodityId());
            Order order = new Order();
            BeanUtils.copyProperties(entity, order);
            order.setCommodityName(commodityEntity.getName());
            order.setPhotoUrl(commodityEntity.getPhotoUrl());
            order.setPrice(commodityEntity.getPrice());
            return order;
        }).collect(Collectors.toList());
        return orders;
    }
    //通过id和状态找到购物车中的商品
    @Override
    public List<Order> getAllByStatus(long buyerId) {
        List<OrderEntity> entities = this.orderRepository.findByBuyerIdAndStatus(buyerId,4);
        List<Order> orders = entities.stream().map(entity -> {
            CommodityEntity commodityEntity = this.commodityRepository.getOne(entity.getCommodityId());
            Order order = new Order();
            BeanUtils.copyProperties(entity, order);
            order.setCommodityName(commodityEntity.getName());
            order.setPhotoUrl(commodityEntity.getPhotoUrl());
            order.setPrice(commodityEntity.getPrice());
            return order;
        }).collect(Collectors.toList());
        return orders;
    }

    @Override
    public Order buyFromCar(CarReq carReq) {
        long commodityId = carReq.getCommodityId();
        int num = carReq.getNum();
        long isSuccessful = this.commodityService.updateQuantity2(commodityId, num);  // 查询商品剩余数量是否够
        if (isSuccessful != -1) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setSellerId(isSuccessful);
            return save2(carReq, orderEntity);
        }
        return null;
    }
    //拒绝原因
    @Override
    public Order getReason(OrderReq orderReq) {
        OrderEntity entity = this.orderRepository.getOne(orderReq.getId());
        entity.setReason(orderReq.getReason());
        this.orderRepository.save(entity);
        Order order = new Order();
        BeanUtils.copyProperties(entity, order);
        return order;
    }
    //获取当前订单信息
    @Override
    public Order getOrderById(long id) {
        OrderEntity orderEntity = this.orderRepository.getOne(id);
        Order order = new Order();
        BeanUtils.copyProperties(orderEntity,order);
        return order;
    }


    /**
     * 更改状态，这里的try catch没有用，需要改
     * @param id    订单id
     * @param status    更改的status
     * @return  是否更改成功
     */
    @Override
    public boolean updateStatus(long id, int status) {
        try {
            OrderEntity entity = this.orderRepository.getOne(id);
//            long comid = entity.getCommodityId();
//            int num = entity.getNum();
//            float pay = commodityService.getPrice(comid) * num;
            entity.setStatus(status);
            this.orderRepository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //取消订单后退钱
    @Override
    public boolean returnMoney(long id) {
        OrderEntity entity = this.orderRepository.getOne(id);
        long comid = entity.getCommodityId();
        long sellId = entity.getSellerId();
        UserEntity entity1 = this.userRepository.getOne(sellId);
        int sellLvl = entity1.getSellerLvl();
        int num = entity.getNum();
        float pay = commodityService.getPrice(comid) * num;
        float pay2 = pay-sellLvl;
        long buyerId = entity.getBuyerId();
        this.userService.getReturnMoney(buyerId,pay);
        this.userService.backMoney(sellId,pay2);
        return true;
    }
    //商家收款
    @Override
    public boolean getMoney(long id) {
        OrderEntity entity = this.orderRepository.getOne(id);
        long comid = entity.getCommodityId();
        int num = entity.getNum();
        float pay = commodityService.getPrice(comid) * num;
        long sellId = entity.getSellerId();
        long buyerId = entity.getBuyerId();
        this.userService.updataScore(buyerId,pay);
        UserEntity userEntity = this.userRepository.getOne(sellId);
        int sellLvl = userEntity.getSellerLvl();
        float pay2 = pay - sellLvl*1;
        this.userService.getReturnMoney(sellId,pay2);
//        entity.setGet(pay2);
//        this.orderRepository.save(entity);
        return true;
    }
}
