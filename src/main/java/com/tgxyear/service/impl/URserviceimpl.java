package com.tgxyear.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tgxyear.mapper.URMapper;
import com.tgxyear.pojo.User_role;
import com.tgxyear.service.URservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class URserviceimpl extends ServiceImpl<URMapper, User_role> implements URservice {


    @Autowired
    URMapper urMapper;
    @Override
    public int adduserroid(int id) {
        return urMapper.adduserroid(id);
    }
}
