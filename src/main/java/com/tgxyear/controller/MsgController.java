package com.tgxyear.controller;

import com.tgxyear.mapper.PhotoMapper;
import com.tgxyear.mapper.UserMapper;
import com.tgxyear.pojo.Photo;
import com.tgxyear.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MsgController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    PhotoMapper photoMapper;

    //处理用户的个人信息

    @GetMapping("/tomamsg")
    public String tomamsg(Model model){
        //获取用户信息
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=(User) principal;
        //这里是解密，绕过了
        User getmyusermsg = userMapper.getmyusermsg(user.getId());

        System.out.println("user=>"+getmyusermsg);
        System.out.println("执行tomasg方法 上面的sout也是===");
        model.addAttribute("user",getmyusermsg);
        List<Photo> photos = photoMapper.selectList(null);
        model.addAttribute("photos",photos);
        return "user/mamsg";
    }


    @PostMapping("/subusermsg")
    public  String subusermsg(User user){
        System.out.println("user=>"+user);
        int i = userMapper.updateallById(user);
        System.out.println("所改变的行数为"+i);
        return "redirect:/toblogdindex";
    }
}
