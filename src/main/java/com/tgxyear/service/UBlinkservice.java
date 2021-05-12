package com.tgxyear.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tgxyear.pojo.UBlink;
import com.tgxyear.pojo.User;

import java.util.List;

public interface UBlinkservice extends IService<UBlink> {
    List<UBlink> getalls();
    int  getuID(int id);
    //获取所有用户信息
    List<User> getalluser();

    //插入ublink表
    boolean insertub(int uid,String blogtitle);
}
