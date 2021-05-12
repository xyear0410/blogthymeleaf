package com.tgxyear.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tgxyear.mapper.BclassifylinkMapper;
import com.tgxyear.mapper.ClassifyMapper;
import com.tgxyear.pojo.B_clink;
import com.tgxyear.service.BclassifylinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BclassifylinkServiceimpl extends ServiceImpl<BclassifylinkMapper, B_clink> implements BclassifylinkService {

    //导入分类表
    @Autowired
    ClassifyMapper classifyMapper;

    //导入博客--分类表
    @Autowired
    BclassifylinkMapper bclassiify;


    @Override
    public List<String> getblogoneclassify(int id) {
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("bid",id);
        List selectList = bclassiify.selectList(wrapper);

        return null;
    }
}
