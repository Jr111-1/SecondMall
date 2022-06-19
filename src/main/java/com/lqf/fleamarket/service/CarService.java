package com.lqf.fleamarket.service;

import com.lqf.fleamarket.controller.param.CarReq;
import com.lqf.fleamarket.domain.model.Car;

import java.util.List;

public interface CarService {
    Car createCar(CarReq carReq);
    void deleteCar(long id);
    List<Car> getAllByBuyerId(long buyerId);
    List<Car> getAllBySellerId(long sellerId);
    List<Car> getAllById(long id);
}
