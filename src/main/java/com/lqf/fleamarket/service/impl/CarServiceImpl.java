package com.lqf.fleamarket.service.impl;

import com.lqf.fleamarket.controller.param.CarReq;
import com.lqf.fleamarket.controller.param.OrderReq;
import com.lqf.fleamarket.dao.entity.CarEntity;
import com.lqf.fleamarket.dao.entity.CommodityEntity;
import com.lqf.fleamarket.dao.entity.OrderEntity;
import com.lqf.fleamarket.dao.repo.CarRepository;
import com.lqf.fleamarket.dao.repo.CommodityRepository;
import com.lqf.fleamarket.domain.model.Car;
import com.lqf.fleamarket.domain.model.Order;
import com.lqf.fleamarket.service.CarService;
import com.lqf.fleamarket.service.CommodityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private CommodityRepository commodityRepository;

    @Override
    public Car createCar(CarReq carReq) {
        long commodityId = carReq.getCommodityId();
        int num = carReq.getNum();
//        this.commodityService.updateSellnum(commodityId,num);
        long isSuccessful = this.commodityService.updateQuantity2(commodityId, num);  // 查询商品剩余数量是否够
        if (isSuccessful != -1) {
            CarEntity carEntity = new CarEntity();
            carEntity.setSellerId(isSuccessful);
            return save(carReq, carEntity);
        }
        return null;
    }

    private Car save(CarReq carReq, CarEntity carEntity) {
        BeanUtils.copyProperties(carReq, carEntity);
        this.carRepository.save(carEntity);
        Car car = new Car();
        BeanUtils.copyProperties(carEntity, car);
        return car;
    }

    @Override
    public void deleteCar(long id) {
//        CarEntity carEntity = this.carRepository.getOne(id);
        this.carRepository.deleteById(id);
//        this.carRepository.delete(carEntity);

    }

    @Override
    public List<Car> getAllByBuyerId(long buyerId) {
        List<CarEntity> entities = this.carRepository.findAllByBuyerId(buyerId);
        List<Car> cars = entities.stream().map(entity -> {
            CommodityEntity commodityEntity = this.commodityRepository.getOne(entity.getCommodityId());
            Car car = new Car();
            BeanUtils.copyProperties(entity, car);
            car.setCommodityName(commodityEntity.getName());
            car.setPhotoUrl(commodityEntity.getPhotoUrl());
            car.setPrice(commodityEntity.getPrice());
            return car;
        }).collect(Collectors.toList());
        return cars;
    }

    @Override
    public List<Car> getAllBySellerId(long sellerId) {
        List<CarEntity> entities = this.carRepository.findAllBySellerId(sellerId);
        List<Car> cars = entities.stream().map(entity -> {
            CommodityEntity commodityEntity = this.commodityRepository.getOne(entity.getCommodityId());
            Car car = new Car();
            BeanUtils.copyProperties(entity, car);
            car.setCommodityName(commodityEntity.getName());
            car.setPhotoUrl(commodityEntity.getPhotoUrl());
            car.setPrice(commodityEntity.getPrice());
            return car;
        }).collect(Collectors.toList());
        return cars;
    }

    @Override
    public List<Car> getAllById(long id) {
        List<CarEntity> entities = this.carRepository.findAllById(id);
        List<Car> cars = entities.stream().map(entity -> {
            CommodityEntity commodityEntity = this.commodityRepository.getOne(entity.getCommodityId());
            Car car = new Car();
            BeanUtils.copyProperties(entity, car);
            car.setCommodityName(commodityEntity.getName());
            car.setPhotoUrl(commodityEntity.getPhotoUrl());
            car.setPrice(commodityEntity.getPrice());
            return car;
        }).collect(Collectors.toList());
        return cars;
    }
}
