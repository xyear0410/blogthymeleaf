package com.tgxyear.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxyear.mapper.ClassifyMapper;
import com.tgxyear.mapper.PhotoMapper;
import com.tgxyear.mapper.UserMapper;
import com.tgxyear.pojo.*;
import com.tgxyear.service.Blogservice;
import com.tgxyear.service.Commentservice;
import com.tgxyear.service.UBlinkservice;
import com.tgxyear.service.URservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

//登录的控制层

@Controller
public class logincontroller {
    @Autowired
    Blogservice blogservice;

    @Autowired
    UBlinkservice uBlinkservice;

    @Autowired
    Commentservice commentservice;

    @Autowired
    UserMapper userMapper;

    @Autowired
    URservice uRservice;
    @Autowired
    PhotoMapper photoMapper;
    @Autowired
    ClassifyMapper classifyMapper;

    //登录跳转
    String logmsg;
    int logint=0;
    @GetMapping("/tologinn")
    public String tologinn(@ModelAttribute("msg")String msg,Model model) {
        System.out.println("msg=>"+msg);
        System.out.println(logmsg);
        int logincurrent =-1;
        if (logmsg =="yes"&&logint ==1){

            logincurrent =1;
        }else if (logmsg =="no"){
            logincurrent =0;
        }

        model.addAttribute("logincurrent",logincurrent);

        logint =0;
        return "login";
    }
    //管理页面跳转
    @RequestMapping("/ad")
    public String toindex(Model model) {
        Object o = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("ads",o);
        return "adindex";
    }

    @GetMapping({"/toblogdindex"})
    public  String toindex01(Model model,
                             @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5")int pageSize,
                             @ModelAttribute(value = "addblogmsg")String addblogmsg){
        //获取用户信息
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=(User) principal;
//        System.out.println("访问了首页");
        Integer userid = userMapper.getoneUserid(user.getName());
        model.addAttribute("user",user);
        model.addAttribute("ads",user.getName());


        //获取博客列表

        //1.分页,博客输出
        Page<Blog> blogPage =new Page<>(pageNum,pageSize);
        IPage<Blog> blogIPage = blogservice.selectPageText(blogPage);
        List<Blog> records = blogIPage.getRecords();
//        List<UBlink> blinks = uBlinkservice.getalls();
        model.addAttribute("blinks",records);

        Integer integer = blogservice.count();
//        System.out.println("总数:"+integer);
//        System.out.println("pageSize:"+pageSize);
        float c = (float)integer / (float)pageSize;
//        System.out.println(c);
        double ceil = Math.ceil(c);
//        System.out.println("向上去整数"+ceil);
        int  a =(int)ceil;
        model.addAttribute("pagecount",a);
        //当前页数
//        System.out.println(pageNum);
        int currentpage =pageNum;
        model.addAttribute("currentpage",currentpage);

        int msgaddblog = -1;
       if (addblogmsg ==null){
           msgaddblog =0;
       }else if (addblogmsg =="yes"){
           msgaddblog=1;
       }
        model.addAttribute("addblogmsg",msgaddblog);

//        System.out.println("addblogmsg=>"+msgaddblog);

        List<Classify> classifies = classifyMapper.selectList(null);
        model.addAttribute("classifies",classifies);

        return "blogindex";
    }


    @GetMapping("/")
    public String iindex(Model model,@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5")int pageSize){
        //获取用户信息
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user=(User) principal;
//        System.out.println("user=>"+user);
//        System.out.println("访问了首页");
//        Integer userid = userMapper.getoneUserid(user.getName());
//        model.addAttribute("user",user);
//        model.addAttribute("ads",user.getName());


        //获取博客列表

        //1.分页,博客输出
        Page<Blog> blogPage =new Page<>(pageNum,pageSize);
        IPage<Blog> blogIPage = blogservice.selectPageText(blogPage);
        List<Blog> records = blogIPage.getRecords();
//        List<UBlink> blinks = uBlinkservice.getalls();
        model.addAttribute("blinks",records);

        Integer integer = blogservice.count();
//        System.out.println("总数:"+integer);
//        System.out.println("pageSize:"+pageSize);
        float c = (float)integer / (float)pageSize;
//        System.out.println(c);
        double ceil = Math.ceil(c);
//        System.out.println("向上去整数"+ceil);
        int  a =(int)ceil;
        model.addAttribute("pagecount",a);
        //当前页数
//        System.out.println(pageNum);
        int currentpage =pageNum;
        model.addAttribute("currentpage",currentpage);
        return "blogindex";
    }

    @GetMapping({"/test", "/test21"})
    public String toese(){
        return "test";
    }

    @GetMapping("/toregisterpage")
    public String toregisterpage(){
        return "register";
    }

    //添加用户
    @PostMapping("/webfrontadduser")
    public  String adduser(User user, RedirectAttributes attributes){
        String result;
        System.out.println("开始执行添加");

        //用户表的添加
        User addu =new User();
        addu.setUsername(user.getUsername());
        addu.setPassword(user.getPassword());
        addu.setEmail(user.getEmail());
        addu.setSex(user.getSex());
        addu.setName(user.getName());
        addu.setEnabled(true);
        addu.setUserpic(photoMapper.selectById(1).getPhoto());
        System.out.println("addu=>"+addu);
        int i1 = userMapper.insert(addu);


        //权限表的增加
        int i = uRservice.adduserroid(addu.getId());
        System.out.println("完成添加操作,该用户的编号为:"+addu.getId());
        if (i1 ==1&&i==1){
            result ="yes";
        }else {
            result="no";
        }
        System.out.println("result:"+result);
        attributes.addFlashAttribute("msg",result);
        logmsg=result;
        logint =1;
        return "redirect:/toblogdindex";
    }

}
