package com.tgxyear.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxyear.mapper.ClassifyMapper;
import com.tgxyear.pojo.Blog;
import com.tgxyear.pojo.Classify;
import com.tgxyear.pojo.User;
import com.tgxyear.service.Blogservice;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
public class EditorController {

    @Autowired
    Blogservice blogservice;
    @Autowired
    ClassifyMapper classifyMapper;

    @GetMapping("/editorblog/{id}")
    public  String editorblog(@PathVariable("id")int id, Model model){
        //上面得id为博客得id


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=(User) principal;
        //此id 是被进入的id
        model.addAttribute("in",user.getId());
        //标题栏显示


        //显示名字
        model.addAttribute("ads",user.getName());

        //查找出in的博客列表
        Page<Blog> blogPage =new Page<>(1,100);

        Blog byId = blogservice.getById(id);

        model.addAttribute("lists",byId);

        //分类
        List<Classify> classifies = classifyMapper.selectList(null);
        model.addAttribute("classifies",classifies);


        //分页
        QueryWrapper<Blog> wrapper = new QueryWrapper();
        wrapper.eq("uid",id);
        //        搜索出当前博客的条数
        int count = blogservice.count(wrapper);
        float c = (float)count / (float)100;
//        System.out.println(c);
        double ceil = Math.ceil(c);
//        System.out.println("向上去整数"+ceil);
        int  a =(int)ceil;
        model.addAttribute("pagecount",a);
        //当前页数
//        System.out.println(pageNum);
        int currentpage =1;
        model.addAttribute("currentpage",currentpage);
        return "personal/editblog";
    }

    @SneakyThrows
    @PostMapping("/fakeform")
    public String fakeform(Blog blog,
                           @RequestParam(value = "file") MultipartFile file,
                           RedirectAttributes ra){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=(User) principal;
        System.out.println(blog);

        //byid是原本得 blog是新的
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
        //标题 分类 内容
        byId.setTitle(blog.getTitle());
        byId.setClassifyid(blog.getClassifyid());
        byId.setContent(blog.getContent());


        System.out.println("byid=>"+byId);
        boolean b = blogservice.updateById(byId);
        String updateStaus;
        if (b){
            updateStaus ="yes";
        }else {
            updateStaus ="no";
        }
        ra.addFlashAttribute("updatestatus",updateStaus);
        return "redirect:/topersonspace/"+user.getId();
    }
}
