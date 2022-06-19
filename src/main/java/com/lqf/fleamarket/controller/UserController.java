package com.lqf.fleamarket.controller;

import com.lqf.fleamarket.controller.param.LoginReq;
import com.lqf.fleamarket.controller.param.RegisterReq;
import com.lqf.fleamarket.controller.param.UserInfoReq;
import com.lqf.fleamarket.controller.param.UserStatusReq;
import com.lqf.fleamarket.dao.entity.UserEntity;
import com.lqf.fleamarket.dao.repo.UserRepository;
import com.lqf.fleamarket.domain.pojo.ResponseCode;
import com.lqf.fleamarket.domain.pojo.ResponseData;
import com.lqf.fleamarket.domain.model.User;
import com.lqf.fleamarket.service.UserService;
import com.lqf.fleamarket.utils.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@Slf4j
public class UserController extends BaseController{

    @Autowired
    private UserRepository userRepository;

    /**
     * @param logInReq login请求，包含邮箱和密码
     * @return 返回请求，记录是否登录成功
     */
    @PostMapping("/user")
    public ResponseData Login(@RequestBody LoginReq logInReq) {
        User user = this.userService.login(logInReq);
        if (user == null) {
            return new ResponseData(ResponseCode.WRONG_LOGIN);//如果用户名或密码错误
        }
        return new ResponseData(ResponseCode.SUCCSEE, user);
    }

    /**
     * 用户注册
     * @param registerReq
     * @return
     */
    @PostMapping("/users")
    public ResponseData Register(@RequestBody RegisterReq registerReq) {
        boolean isExist = this.userService.checkUser(registerReq.getEmail());
        if (!isExist) { //如果不存在，则进行注册
            User user = this.userService.registerUser(registerReq);
            return new ResponseData(ResponseCode.SUCCSEE, user);
        }
        return new ResponseData(ResponseCode.WRONG_REGISTER);  //邮箱已注册过
    }

    /**
     * 上传用户头像
     * @param id
     * @param file
     * @return
     */
    @PostMapping("/users/{id}/images")
    public ResponseData uploadImg(@PathVariable long id, @RequestParam("file")MultipartFile file) {
        try {
            String url = ImageUtil.uploadImg(file, ImageUtil.USER_PROFILE);
            if (this.userService.saveImgURL(id, url)) {
                return new ResponseData(ResponseCode.SUCCSEE, url);
            } else {
                return new ResponseData(ResponseCode.BAD_REQUEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseData(ResponseCode.BAD_REQUEST);
        }
    }

    @PostMapping("/users/{id}/Licenseimages")
    public ResponseData uploadLicenseImg(@PathVariable long id, @RequestParam("file")MultipartFile file) {
        try {
            String url = ImageUtil.uploadImg(file, ImageUtil.USER_PROFILE);
            if (this.userService.saveLiceseURL(id, url)) {
                return new ResponseData(ResponseCode.SUCCSEE, url);
            } else {
                return new ResponseData(ResponseCode.BAD_REQUEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseData(ResponseCode.BAD_REQUEST);
        }
    }

    @PostMapping("/users/{id}/idimages")
    public ResponseData uploadIdImg(@PathVariable long id, @RequestParam("file")MultipartFile file) {
        try {
            String url = ImageUtil.uploadImg(file, ImageUtil.USER_PROFILE);
            if (this.userService.saveIdURL(id, url)) {
                return new ResponseData(ResponseCode.SUCCSEE, url);
            } else {
                return new ResponseData(ResponseCode.BAD_REQUEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseData(ResponseCode.BAD_REQUEST);
        }
    }

    /**
     * 获取正在审核中的用户
     * @return
     */
    @GetMapping("/users")
    public ResponseData getRegUsers() {
        return new ResponseData(ResponseCode.SUCCSEE, this.userService.getUsersByRole(UserService.STATUS_AUDIT));
    }
    //管理员查看所有已注册的用户
    @GetMapping("/users/all/")
    public ResponseData getUsers() {
        return new ResponseData(ResponseCode.SUCCSEE, this.userService.getUsersByRole(UserService.STATUS_COMMON));
    }

    @GetMapping("/users/checkLicense/")
    public ResponseData getUsersByLicense() {
        return new ResponseData(ResponseCode.SUCCSEE, this.userService.getUsersByLicense(0));
    }


    //管理员设置用户状态
    @PutMapping("/users/{id}")
    public ResponseData setUserRole(@PathVariable("id") long id, @RequestBody UserStatusReq userStatusReq) {
        if (this.userService.setUserStatus(id, userStatusReq.getRole())){
            return new ResponseData(ResponseCode.SUCCSEE);
        }
        return new ResponseData(ResponseCode.SERVER_WRONG);
    }
    //管理员禁用用户状态
    @PutMapping("/users/ban/{id}")
    public ResponseData banUserRole(@PathVariable("id") long id) {
        if (this.userService.setUserStatus(id, -1)){
            return new ResponseData(ResponseCode.SUCCSEE);
        }
        return new ResponseData(ResponseCode.SERVER_WRONG);
    }

    @PutMapping("/users/License/{id}")
    public ResponseData isLicense(@PathVariable("id") long id){
        if (this.userService.setLicenseStatus(id,0))
            return new ResponseData(ResponseCode.SUCCSEE);
        return new ResponseData(ResponseCode.SERVER_WRONG);
    }

    @PutMapping("/users/License2/{id}")
    public ResponseData isLicense2(@PathVariable("id") long id){
        if (this.userService.setLicenseStatus(id,1))
            return new ResponseData(ResponseCode.SUCCSEE);
        return new ResponseData(ResponseCode.SERVER_WRONG);
    }

    //修改个人信息
    @PutMapping("/users/change/")
    public ResponseData ChangeInfos(@RequestBody UserInfoReq userInfoReq){
        return new ResponseData(ResponseCode.SUCCSEE,this.userService.changeInfos(userInfoReq));
//        User user = this.userService.changeInfos(userInfoReq);
//        return new ResponseData(ResponseCode.SUCCSEE, user);

    }
    //存钱
    @PutMapping("/users/save/")
    public ResponseData saveMoney(@RequestBody UserInfoReq userInfoReq){
        return new ResponseData(ResponseCode.SUCCSEE,this.userService.saveMoney(userInfoReq));
    }
    //刷新个人信息
    @PostMapping("/users/getInfo/{id}")
    public ResponseData getInfo(@RequestBody UserInfoReq userInfoReq){
        return new ResponseData(ResponseCode.SUCCSEE,this.userService.getInfo(userInfoReq));
    }
    //管理者查看个人信息
    @GetMapping("/users/getInfos/{id}")
    public ResponseData getInfos(@PathVariable long id){
        return new ResponseData(ResponseCode.SUCCSEE,this.userService.getInfos(id));
    }

    @PutMapping("/users")
    public ResponseData payMoney(@PathVariable("id") long id,@PathVariable("money") long money){
        return new ResponseData(ResponseCode.SUCCSEE,this.userService.payMoney(id,money));
    }


}
