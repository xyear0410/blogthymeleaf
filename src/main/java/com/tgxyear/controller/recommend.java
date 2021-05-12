package com.tgxyear.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxyear.mapper.BlogMapper;
import com.tgxyear.mapper.LabelViewMapper;
import com.tgxyear.mapper.Labelidmapper;
import com.tgxyear.mapper.Labelmapper;
import com.tgxyear.pojo.Blog;
import com.tgxyear.pojo.Labelview;
import com.tgxyear.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Random;

@Controller
public class recommend {

    @Autowired
    BlogMapper blogMapper;
    @Autowired
    Labelmapper labelmapper;
    @Autowired
    Labelidmapper labelidmapper;
    @Autowired
    LabelViewMapper labelViewMapper;

    @GetMapping("/torecommend")
    public String torecommend(Model model){
        //进来的用户
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=(User) principal;
        List<Blog> records;
        QueryWrapper<Blog> maxgreat =new QueryWrapper<>();
        maxgreat.orderByDesc("bgreat");
        Page<Blog> page =new Page<>(1,8);
        List<Blog> daliys =blogMapper.selectPage(page, maxgreat).getRecords();;
        List<Labelview> getlid = labelViewMapper.getlid(user.getId());
        //获得lid的数列
        if (getlid.isEmpty()){
            System.out.println("为空");
            records = blogMapper.selectPage(page, maxgreat).getRecords();
        }else if (getlid.size() ==1){
            System.out.println("当前所浏览的标签数量为1");
           //获得bid
            records =  labelidmapper.getbattle( getlid.get(0).getLid());
        }else{
            System.out.println("lid=>"+getlid);
            System.out.println(getlid.size());
            Random random  =new Random();
            //随机生产
            int randNum =random.nextInt(getlid.size());
            System.out.println("randnUM"+randNum);
            records =  labelidmapper.getbattle(randNum+1);
//            System.out.println("records=>"+records);
        }

        model.addAttribute("records",records);
        model.addAttribute("daliys",daliys);
        return "recommend";
    }
}
