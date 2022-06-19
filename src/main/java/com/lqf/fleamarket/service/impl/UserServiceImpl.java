package com.lqf.fleamarket.service.impl;

import com.lqf.fleamarket.controller.param.LoginReq;
import com.lqf.fleamarket.controller.param.RegisterReq;
import com.lqf.fleamarket.controller.param.UserInfoReq;
import com.lqf.fleamarket.dao.entity.UserEntity;
import com.lqf.fleamarket.dao.repo.UserRepository;
import com.lqf.fleamarket.domain.UserDTO;
import com.lqf.fleamarket.domain.model.User;
import com.lqf.fleamarket.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(RegisterReq registerReq){
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(registerReq, userEntity);
        userEntity.setRole(-1);
        userEntity.setIsLicense(-1);
        userEntity.setSellerLvl(5);
        userEntity = this.userRepository.save(userEntity);
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }

    @Override
    public User login(LoginReq loginReq) {
        UserEntity userEntity = this.userRepository.findByEmailAndPassword(loginReq.getEmail(), loginReq.getPassword());
        if (userEntity != null) {
            if (userEntity.getRole() == loginReq.getRole()) {
                User user = new User();
                BeanUtils.copyProperties(userEntity, user);
                return user;
            } else if (userEntity.getRole() == 1) {
                User user = new User();
                BeanUtils.copyProperties(userEntity, user);
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean checkUser(String email) {
        UserEntity userEntity = this.userRepository.findByEmail(email);
        if (userEntity != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean saveImgURL(long id, String url) {
        UserEntity entity = this.userRepository.getOne(id);
        entity.setPhotoUrl(url);
        this.userRepository.save(entity);
        return true;
    }

    @Override
    public boolean saveLiceseURL(long id, String url) {
        UserEntity entity = this.userRepository.getOne(id);
        entity.setLicense_photo(url);
        this.userRepository.save(entity);
        return true;
    }

    @Override
    public boolean saveIdURL(long id, String url) {
        UserEntity entity = this.userRepository.getOne(id);
        entity.setIdPhoto(url);
        this.userRepository.save(entity);
        return true;
    }

    /**
     *
     * @param role 用户的状态，0为普通用户，1为管理员，-1为注册审核中的用户
     * @return
     */
    @Override
    public List<UserDTO> getUsersByRole(int role) {
        List<UserEntity> userEntities = this.userRepository.findAllByRole(role);
        List<UserDTO> users = userEntities.stream().map(entity -> {
            UserDTO user = new UserDTO();
            BeanUtils.copyProperties(entity, user);
            return user;
        }).collect(Collectors.toList());
        return users;
    }

    @Override
    public List<UserDTO> getUsersByLicense(int license) {
        List<UserEntity> userEntities = this.userRepository.findAllByIsLicense(license);
        List<UserDTO> users = userEntities.stream().map(entity -> {
            UserDTO user = new UserDTO();
            BeanUtils.copyProperties(entity, user);
            return user;
        }).collect(Collectors.toList());
        return users;
    }

    @Override
    public boolean setUserStatus(long id, int role) {
        UserEntity entity = this.userRepository.getOne(id);
        entity.setRole(role);
        this.userRepository.save(entity);
        return true;
    }

    @Override
    public boolean setLicenseStatus(long id, int num) {
        UserEntity entity = this.userRepository.getOne(id);
        int license = entity.getIsLicense();
        entity.setIsLicense(num);
        this.userRepository.save(entity);
        return true;
    }

    //修改用户个人信息
    @Override
    public User changeInfos(UserInfoReq userInfoReq) {
        UserEntity entity = this.userRepository.findAllById(userInfoReq.getId());
        entity.setBankid(userInfoReq.getBankid());
        entity.setBiography(userInfoReq.getBiography());
        entity.setCity(userInfoReq.getCity());
        entity.setName(userInfoReq.getName());
        entity.setTel(userInfoReq.getTel());
        entity.setSex(userInfoReq.getSex());
        entity.setXingming(userInfoReq.getXingming());
        entity.setSellerLvl(userInfoReq.getSellerLvl());
        entity.setPassword(userInfoReq.getPassword());
        this.userRepository.save(entity);
        User user = new User();
        BeanUtils.copyProperties(entity, user);
        return user;

    }
    //存钱
    @Override
    public User saveMoney(UserInfoReq userInfoReq) {
        UserEntity entity = this.userRepository.findAllById(userInfoReq.getId());
        float money = entity.getMoney();
        entity.setMoney(money + userInfoReq.getMoney());
        String payMoney = String.valueOf(userInfoReq.getMoney());
        String saveContent = userInfoReq.getSaveContent();
        entity.setSaveContent(saveContent  + "," + "存入"+payMoney + "元");
        this.userRepository.save(entity);
        User user = new User();
        BeanUtils.copyProperties(entity, user);
        return user;
    }
    //获取退款，商家收款
    @Override
    public void getReturnMoney(long id, float money) {
        UserEntity entity = this.userRepository.getOne(id);
        float nowMoney = entity.getMoney();
        entity.setMoney(money+nowMoney);
        String saveContent = entity.getSaveContent();
        String payMoney = String.valueOf(money);
        entity.setSaveContent(saveContent  + "," + "存入"+payMoney + "元");
        this.userRepository.save(entity);

    }

    @Override
    public void backMoney(long id, float money) {
        UserEntity entity = this.userRepository.getOne(id);
        float nowMoney = entity.getMoney();
        entity.setMoney(nowMoney - money);
        this.userRepository.save(entity);
    }

    @Override
    public User payMoney(long id, float money) {
        UserEntity entity = this.userRepository.findAllById(id);
        String before = entity.getPayContent();
        entity.setPayContent(before+","+"消费"+money+"元");
        this.userRepository.save(entity);
        User user = new User();
        BeanUtils.copyProperties(entity,user);

        return user;
    }

    //刷新信息
    @Override
    public User getInfo(UserInfoReq userInfoReq) {
        UserEntity entity = this.userRepository.findAllById(userInfoReq.getId());
        User user = new User();
        BeanUtils.copyProperties(entity,user);
        return user;
    }
    //管理员查看用户信息
    @Override
    public User getInfos(long id) {
        UserEntity entity = this.userRepository.findAllById(id);
        User user = new User();
        BeanUtils.copyProperties(entity,user);
        return user;
    }
    //确认 余额是否足够购买
    @Override
    public long updataMoney(long id, float pay) {
        try {
            UserEntity userEntity = userRepository.getOne(id);
            float money = userEntity.getMoney();
            if (money >= pay) {
                userEntity.setMoney(money - pay);
                this.userRepository.save(userEntity);
                return userEntity.getId();

            }
            return -1;  //如果不够返回-1
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void updataScore(long id, float pay) {
        UserEntity userEntity = userRepository.getOne(id);
        float score = userEntity.getScore();
        userEntity.setScore(score+pay);
        this.userRepository.save(userEntity);

    }


}
