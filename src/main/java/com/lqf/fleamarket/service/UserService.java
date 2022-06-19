package com.lqf.fleamarket.service;

import com.lqf.fleamarket.controller.param.LoginReq;
import com.lqf.fleamarket.controller.param.RegisterReq;
import com.lqf.fleamarket.controller.param.UserInfoReq;
import com.lqf.fleamarket.domain.UserDTO;
import com.lqf.fleamarket.domain.model.User;

import java.util.List;


public interface UserService {
    int STATUS_COMMON = 0, STATUS_ADMIN = 1, STATUS_AUDIT = -1; // 用户的状态，0为普通用户，1为管理员，-1为注册审核中的用户

    User registerUser(RegisterReq registerReq);//注册一个用户
    User login(LoginReq loginReq);//用户登录
    boolean checkUser(String email);//检查用户是否存在
    boolean saveImgURL(long id, String url);
    boolean saveLiceseURL(long id, String url);
    boolean saveIdURL(long id, String url);
    List<UserDTO> getUsersByRole(int role);// 获取申请某种状态的的用户
    List<UserDTO> getUsersByLicense(int license);
    boolean setUserStatus(long id, int role); // 设置用户的状态信息
    boolean setLicenseStatus(long id, int num);
    User changeInfos(UserInfoReq userInfoReq);//用于修改用户信息
    User saveMoney(UserInfoReq userInfoReq);
    void getReturnMoney(long id, float money);
    void backMoney(long id, float money);//退货后从商家账户扣款
    User payMoney(long id, float money);
    User getInfo(UserInfoReq userInfoReq);
    User getInfos(long id);
    long updataMoney(long id, float pay);
    void updataScore(long id, float pay);
}
