package com.tgxyear.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tgxyear.mapper.BlogMapper;
import com.tgxyear.mapper.UBlinkMapper;
import com.tgxyear.mapper.UserMapper;
import com.tgxyear.pojo.Blog;
import com.tgxyear.pojo.UBlink;
import com.tgxyear.pojo.User;
import com.tgxyear.service.UBlinkservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UBlinkserviceimpl extends ServiceImpl<UBlinkMapper, UBlink> implements UBlinkservice {
    @Autowired
    UBlinkMapper uBlinkMapper;
    @Autowired
    BlogMapper blogMapper;

    @Override
    public List<UBlink> getalls() {
        List<UBlink> getalls = uBlinkMapper.getalls();

        return getalls;
    }

    @Override
    public int getuID(int id) {
        return uBlinkMapper.getuID(id);
    }

//    获取所有用户信息
    @Override
    public List<User> getalluser() {
        return uBlinkMapper.getalluser();
    }

    @Override
    public boolean insertub(int uid, String blogtitle) {
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("title",blogtitle);
        boolean b = uBlinkMapper.insertub(uid, blogMapper.selectOne(wrapper).getId());
        return b;
    }


}
