package com.tgxyear.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxyear.mapper.*;
import com.tgxyear.pojo.*;
import com.tgxyear.service.Blogservice;
import com.tgxyear.service.UBlinkservice;
import com.tgxyear.service.URservice;
import com.tgxyear.utils.Commentvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;


//后台页面控制层
@Controller
public class managehomecontroller {

    @Autowired
    UBlinkservice uBlinkservice;
    @Autowired
    Blogservice blogservice;
    @Autowired
    URMapper urMapper;

    @Autowired
    URservice uRservice;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ClassifyMapper classifyMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    PhotoMapper photoMapper;
    String result;
    //获取用户列表
    @GetMapping("/touserlist")
    public String touserlist(Model model, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                             @ModelAttribute("msg")String msg){
//        获取当前用户的姓名
        Object o = SecurityContextHolder.getContext().getAuthentication().getName();
//        model.addAttribute("ads",o);

        model.addAttribute("msg",msg);

        //自定义分页
        Page<User> userPage =new Page<>(pageNum,pageSize);
        IPage<User> userIPage = urMapper.selectPageVo(userPage);
        //获取当前用户信息
        List<User> records = userIPage.getRecords();
        model.addAttribute("alluser",records);
        Integer integer = urMapper.selectcountid();
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

        return "user/userlist";
    }

    //跳转查询页面
    @GetMapping("/tosearchad")
    public String tosearchad(Model model,String inputname,String inputusername){
        inputname= inputname.trim();
        inputusername=inputusername.trim();
        System.out.println("inputname=>"+inputname+"inputusername=>"+inputusername);

//        List<Classify> classifies = classifyMapper.selectList(null);
//        model.addAttribute("classifies",classifies);
        return "user/userlist";
    }

    //test 模糊
    @PostMapping("/searched")
    public String searched(String inputname,int one,
                           int two,Model model){
        System.out.println("inputname=>"+inputname);
        System.out.println("one=>"+one);
        System.out.println("two=>"+two);
        inputname = inputname.trim();
        //赋予得出值


        return "searchad";
    }

    //模糊查询
    @GetMapping("/selectad")
    public String selectoneuser(String inputname,String inputusername,
                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                @ModelAttribute("msg")String msg,Model model){



        //除空格
        inputname=  inputname.trim();
        inputusername=inputusername.trim();
        if ( inputname.isEmpty()){
            System.out.println("name的值为空");
        }
        model.addAttribute("msg",msg);
        System.out.println("name=>"+ inputname+"username=>"+inputusername);
        IPage<User> page =new Page<>(pageNum,pageSize);
        List<User> userList = userMapper.selectadpageuser(page,  inputname, inputusername);
        model.addAttribute("alluser",userList);
        Integer integer = userMapper.countadpageuser( inputname,inputusername);
//        System.out.println("总数:"+integer);
//        System.out.println("pageSize:"+pageSize);

        float c = (float)integer / (float)pageSize;
//        System.out.println(c);
        double ceil = Math.ceil(c);
//        System.out.println("向上去整数"+ceil);
        int  a =(int)ceil;
        model.addAttribute("pagecount",a);

        //当前页数

        int currentpage =pageNum;
        model.addAttribute("currentpage",currentpage);

        return "user/userlist";
    }

    //编辑
    @RequestMapping("/toedituser")
    public  String toedituser(int id,Model model){
        Object o = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("ads",o);
        System.out.println("取得的用户值为:"+id);

        User user = userMapper.selectoneuser(id);
        model.addAttribute("user",user);
        return "user/edituser";
    }

    @PostMapping("/edituser")
    public String edituser(User user, RedirectAttributes attributes){

        System.out.println(user);
        System.out.println("状态"+user.getenable());
        if (user.getenable()==false){
            System.out.println("禁言");
            user.setEnabled(false);
        }else {
            System.out.println("未禁");
            user.setEnabled(true);
        }

        boolean updateuser = userMapper.updateuser(user);
        System.out.println("修改结果为:"+updateuser);
        if(updateuser){
            result ="修改成功";
        }else {
            result ="修改失败";
        }
        attributes.addFlashAttribute("msg",result);
        return "redirect:/touserlist";
    }

    //添加用户
    @PostMapping("/adduser")
    public  String adduser(User user,RedirectAttributes attributes){
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

        int i1 = userMapper.insert(addu);

        //权限表的增加
        int i = uRservice.adduserroid(addu.getId());
        System.out.println("完成添加操作,该用户的编号为:"+addu.getId());
        if (i1 ==1&&i==1){
            result ="添加成功";
        }else {
            result="添加失败";
        }
        attributes.addFlashAttribute("msg",result);
        return "redirect:/touserlist";
    }

    //删除用户
    @PostMapping("/deleteuser")
    public String deleteuser(Integer delid,RedirectAttributes attributes){
        System.out.println("取得得值为"+delid);
        int delete = userMapper.deleteById(delid);
        if (delete==1){
            result ="删除成功";
        }else {
            result="删除失败";
        }
        attributes.addFlashAttribute("msg",result);
        return "redirect:/touserlist";
    }

    //管理员列表 --评论
    @GetMapping("/tocommentlist")
    public String tocommentlist(Model model,@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "5")int pageSize){
        IPage<Comment> page =new Page<>(pageNum,pageSize);
        List<Comment> list = commentMapper.findallcomment(page);
        model.addAttribute("commentlists",list);
        //数出总数

        Integer integer = commentMapper.selectCount(null);
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
        return "adcomment";
    }


    //提供具体某一个信息 用户模态框
    @GetMapping("/findonecommentvo")
    @ResponseBody
    public Commentvo findonecommentvo(int commentid){
        Commentvo data = commentMapper.findOneById(commentid);


        System.out.println("获取到"+data);
        return data;
    }

    //编辑模态框提交
    @PostMapping("/tosubmitcomment")
    public String tosubmitcomment(Comment comment){
        System.out.println("======获取值中=======");
        System.out.println("comment=>"+comment);
//        Comment select = commentMapper.selectById(comment.getId());
//        select.setContent(comment.getContent());
        commentMapper.updateContentById(comment.getContent(),comment.getId());
        return "redirect:/tocommentlist";
    }

    //删除
    @ResponseBody
    @GetMapping("/todelonecomment")
    public int todelonecomment(int delcommentid){
        System.out.println("delcommentid=>"+delcommentid);
        int i = commentMapper.deleteById(delcommentid);

        if (i==1){
            result ="删除成功";
        }else {
            result="删除失败";
        }


        return i ;
    }
}


//message:' 操作成功',    //提示信息
//        duration:'5000',       //显示时间（默认：5s）
//        type:'info',           //显示类型，包括4种：success.error,info,warning 默认info
//        showClose:false,       //显示关闭按钮（默认：否）
//        center:true,           //页面竖直居中（默认：否）
//        onClose:function,      //点击关闭回调函数