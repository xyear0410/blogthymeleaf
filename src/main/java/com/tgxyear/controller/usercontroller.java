package com.tgxyear.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tgxyear.mapper.UserMapper;
import com.tgxyear.pojo.UBlink;
import com.tgxyear.pojo.User;
import com.tgxyear.service.UBlinkservice;
import com.tgxyear.service.URservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class usercontroller {

    @Autowired
    UserMapper userMapper;

    @Autowired
    URservice uRservice;

    @Autowired
    UBlinkservice uBlinkservice;
    //用户添加
//    @PostMapping("/adduser")
//    public  String adduser(User user){
//        System.out.println("开始执行添加");
//        //用户表的添加
//       User addu =new User();
//       addu.setUsername(user.getUsername());
//       addu.setPassword(user.getPassword());
//       addu.setEmail(user.getEmail());
//       addu.setSex(user.getSex());
//       addu.setName(user.getName());
//       addu.setEnabled(true);
//       userMapper.insert(addu);
//
//       //权限表的增加
//        uRservice.adduserroid(addu.getId());
//        System.out.println("完成添加操作,该用户的编号为:"+addu.getId());
//
//        return "redirect:/touserlist";
//    }



    @RequestMapping("/toload")
    public  String toload(Model model){
      model.addAttribute("msg","进来了");
        return "upload";
    }
}
