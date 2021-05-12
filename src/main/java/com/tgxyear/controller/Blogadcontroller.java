package com.tgxyear.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxyear.mapper.BclassifylinkMapper;
import com.tgxyear.mapper.BlogMapper;
import com.tgxyear.mapper.ClassifyMapper;
import com.tgxyear.mapper.UserMapper;
import com.tgxyear.pojo.*;
import com.tgxyear.service.Blogservice;
import com.tgxyear.service.UBlinkservice;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.List;
import java.util.UUID;


//博客管理控制层
@Controller
public class Blogadcontroller {


    @Autowired
    Blogservice blogservice;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    ClassifyMapper classifyMapper;
    @Autowired
    BclassifylinkMapper bc;
    @Autowired
    UBlinkservice uBlinkservice;



    //跳转到列表
    // 获取博客列表
    @GetMapping("/getbot")
    public String getbot(Model model,
                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "5")int pageSize,
                            @ModelAttribute("delblogmsg")String delblogmsg){
        Page<BlogVo> blogPage = new Page<>(pageNum, pageSize);
        List<BlogVo> blogVoList = blogMapper.getallblogusers(blogPage);
        model.addAttribute("blinks",blogVoList);



        //分页总数
        Integer integer = blogMapper.selectCount(null);
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

        //当前用户的名字
        Object o = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("ads",o);

        //分类
        List<Classify> classifies = classifyMapper.selectList(null);
        model.addAttribute("classifies",classifies);

        //删除信息的更改
        model.addAttribute("dels",delblogmsg);

        return "boottest/bottest";
    }

//    模糊查询
    @PostMapping("/searchblog")
    public String searchblog(String blogtitle,String blogauthor,
                             @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5")int pageSize,Model model){
        System.out.println("blogtitle=>"+blogtitle+"blogcontent=>"+blogauthor);

        IPage<Blog> blogIPage =new Page<>(pageNum,pageSize);
        List<Blog> list = blogMapper.selectadpageblog(blogIPage, blogtitle, blogauthor);
        model.addAttribute("blinks",list);
        int integer = blogMapper.countselectadpageblog(blogtitle, blogauthor);
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

        //当前用户的名字
        Object o = SecurityContextHolder.getContext().getAuthentication().getName();
//        model.addAttribute("ads",o);

        //分类
        List<Classify> classifies = classifyMapper.selectList(null);
        model.addAttribute("classifies",classifies);
        return "boottest/bottest";
    }


    //跳转添加页面
    @GetMapping("/toadd")
    public String toadd(Model model){
        String o = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("ads",o);
        List<Classify> classifies = classifyMapper.selectList(null);
        model.addAttribute("classifies",classifies);
        return "boottest/add";
    }

    //添加操作！
    @SneakyThrows
    @PostMapping("/toadd")
    public String toaddBlog(Blog blog, @RequestParam(value = "file") MultipartFile file, Model model){
        String o = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer userid = userMapper.getoneUserid(o);
        System.out.println("当前用户id" +userid);

        System.out.println("======添加==============");
        System.out.println(blog);
        System.out.println(file);
        //保存数据库的路径
        String sqlPath = null;
        //定义文件保存的本地路径
        String localPath = "G:\\imagehome\\";
        //定义 文件名
        String filename = null;
        //定义 返回信息
        String returnmsg ;
        if (!file.isEmpty()) {
            //生成uuid作为文件名称    随机生成的图片名字
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //获得文件类型（可以判断如果不是图片，禁止上传）
            String contentType = file.getContentType();
            //获得文件后缀名获取到jpg或者是其他格式
            String suffixName = contentType.substring(contentType.indexOf("/") + 1);  // mingzi.jpg 保留jpg
            //得到 文件名  一群随机数字加上他的格式 如xxxxx.jpg
            filename = uuid + "." + suffixName;
            System.out.println(filename);
            //文件保存路径  将图片保存在D盘的file中
            file.transferTo(new File(localPath + filename));
        } else {
            System.out.println("文件为空");
        }
        //把图片的相对路径保存至数据库
        sqlPath = "/image/" + filename;

        System.out.println(sqlPath);
        blog.setPicture(sqlPath);
        //博客表
        blog.setUid(userid);
        boolean save = blogservice.save(blog);


        //连接表

//       uBlinkservice.insertub(userid,blog.getTitle());
        if (save){

            System.out.println("添加成功");
            returnmsg="添加成功";
        }else {
            returnmsg="添加失败";
            System.out.println("添加失败");
        }

        System.out.println("======结束==============");



        return "redirect:/getbot";
    }



    //修改  @{/emp/(${emp.id})}


    //去修改的页面
    @GetMapping("/toupdateblog/{id}")
    public  String toupdateBlog(@PathVariable("id") Integer id,Model model){
        System.out.println("在更改页面路上");
        //用户姓名
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=(User) principal;
        model.addAttribute("ads",user.getName());
        //获取博客数据
        BlogVo one = blogMapper.selectblogmsg(id);
        model.addAttribute("blog",one);

        System.out.println("blog=>"+one);


        //分类
        List<Classify> classifies = classifyMapper.selectList(null);
        model.addAttribute("classifies",classifies);



        model.addAttribute("uname",user.getName());
        return "boottest/update";
    }

    //更新de 弹窗 ,信息的获取
    @GetMapping("/getVoblog/{id}")
    @ResponseBody
    public BlogVo getVoblog(@PathVariable("id")int id){
        BlogVo vo = blogMapper.selectblogmsg(id);


        //作者
//        String name = userMapper.getUsername(blog.getUid());
        // 类别id
//        blog.getClassifyid();
//        QueryWrapper wrapper =new QueryWrapper();
//        wrapper.eq("classifyid",blog.getClassifyid());
//

//        B_clink b_clink = bc.selectOne(wrapper);
        //类别名字

        return vo;
    }


    //获取所有标签,用于模态框得遍历
    @PostMapping("/getallclassify")
    @ResponseBody
    public List<Classify>  getallclassify(){
        List<Classify> data = classifyMapper.selectList(null);

        return data;
    }

    //更新操作
    @SneakyThrows
    @PostMapping("/updateblog")
    public String updateblog(Blog blog,
                            @RequestParam(value = "file") MultipartFile file){
        System.out.println("控制层执行更新方法");
        System.out.println("更新成功"+blog);

        Blog byId = blogservice.getById(blog.getId());

        //图片
        System.out.println(file);
        //保存数据库的路径
        String sqlPath = null;
        //定义文件保存的本地路径
        String localPath = "G:\\imagehome\\";
        //定义 文件名
        String filename = null;
        //定义 返回信息
        String returnmsg ;

        if (!file.isEmpty()) {
            //生成uuid作为文件名称    随机生成的图片名字
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //获得文件类型（可以判断如果不是图片，禁止上传）
            String contentType = file.getContentType();
            //获得文件后缀名获取到jpg或者是其他格式
            String suffixName = contentType.substring(contentType.indexOf("/") + 1);  // mingzi.jpg 保留jpg
            //得到 文件名  一群随机数字加上他的格式 如xxxxx.jpg
            filename = uuid + "." + suffixName;
            System.out.println(filename);
            //文件保存路径  将图片保存在D盘的file中
            file.transferTo(new File(localPath + filename));
            sqlPath = "/image/" + filename;
            System.out.println("文件名字"+filename);
            System.out.println(sqlPath);
            byId.setPicture(sqlPath);
        } else {
            System.out.println("上传更改文件为空");
        }
        //更改操作
        byId.setTitle(blog.getTitle());
        byId.setContent(blog.getContent());

        //把图片的相对路径保存至数据库
        boolean b = blogservice.updateById(byId);
        System.out.println("修改结果为"+b);



        return "redirect:/getbot";
    }



    //删除
    @PostMapping("/deletedblog")
    public String deletedblog(int delid, RedirectAttributes redirectAttributes){
        //博客的id
        System.out.println("该删除的id为"
                +delid);
        int i = blogMapper.deleteById(delid);
        redirectAttributes.addFlashAttribute("delblogmsg",i);
        System.out.println("删除的行数"+i);
        return "redirect:/getbot";
    }


}
