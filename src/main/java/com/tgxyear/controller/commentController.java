package com.tgxyear.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tgxyear.mapper.UserMapper;
import com.tgxyear.pojo.Comment;
import com.tgxyear.service.Commentservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tgxyear.controller.webfrontcontroller.*;
import java.util.List;


//测试的。
@Controller
public class commentController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    Commentservice commentservice;


    @GetMapping("/totestcomment")
    public String totestcomment(){
        return "testcomment";
    }






}
