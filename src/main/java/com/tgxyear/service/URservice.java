package com.tgxyear.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tgxyear.pojo.User_role;

public interface URservice extends IService<User_role> {
    int adduserroid(int id);
}
