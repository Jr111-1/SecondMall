package com.lqf.fleamarket.controller;

import com.lqf.fleamarket.controller.param.CarReq;
import com.lqf.fleamarket.domain.model.Car;
import com.lqf.fleamarket.domain.pojo.ResponseCode;
import com.lqf.fleamarket.domain.pojo.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class CarController extends BaseController {

    @PostMapping("/cars")
    public ResponseData createCar(@RequestBody CarReq carReq) {
        Car car = this.carService.createCar(carReq);
        if (car != null) {
            return new ResponseData(ResponseCode.SUCCSEE, car);
        }
        return new ResponseData(ResponseCode.BAD_REQUEST);
    }

    @DeleteMapping("/cars/{id}")
    public ResponseData deleteOrder(@PathVariable("id") long id) {
        this.carService.deleteCar(id);
        return new ResponseData(ResponseCode.SUCCSEE);
    }

    @GetMapping("/buyer-cars/{buyerId}")
    public ResponseData getAllByBuyerId(@PathVariable long buyerId) {
        return new ResponseData(ResponseCode.SUCCSEE, this.carService.getAllByBuyerId(buyerId));
    }

    @GetMapping("/buy/{Id}")
    public ResponseData getAllById(@PathVariable("id") long id) {
        return new ResponseData(ResponseCode.SUCCSEE, this.carService.getAllById(id));
    }


    @GetMapping("/pending-cars/{sellerId}")
    public ResponseData getAllBySellerId(@PathVariable long sellerId) {
        return new ResponseData(ResponseCode.SUCCSEE, this.carService.getAllBySellerId(sellerId));
    }

}
