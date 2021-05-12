package com.tgxyear.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tgxyear.mapper.BclassifylinkMapper;
import com.tgxyear.mapper.BlogMapper;
import com.tgxyear.mapper.ClassifyMapper;
import com.tgxyear.pojo.Blog;
import com.tgxyear.pojo.Classify;
import com.tgxyear.service.Blogservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


//分类的控制层

@Controller
public class ClassifyController {

    @Autowired
    ClassifyMapper classifyMapper;

    @Autowired
    BclassifylinkMapper bcMapper;
    @Autowired
    Blogservice blogservice;


    //用户跳转
    @GetMapping("/indexclassify")
    public String toclassify(Model model){
        List<Classify> classifies = classifyMapper.selectList(null);
        model.addAttribute("classifies",classifies);
        return "classifyad/indexclassify";
    }
    //对应列表跳转
    @GetMapping("/toblogindexclassify/{id}")
    public String toblogindexclassify(@PathVariable("id")int id,Model model){
        QueryWrapper<Blog> wrapper =new QueryWrapper<>();
        wrapper.eq("classifyid",id)
                .orderByDesc("createtime");
        List<Blog> lists = blogservice.list(wrapper);
        int count = blogservice.count(wrapper);
        model.addAttribute("lists",lists);
        model.addAttribute("count",count);
        return "classifyad/classifymsg";
    }



    //管理员  跳转分类页面
    @GetMapping("/toclassify")
    public String toclassify(Model model,
                             @ModelAttribute("updateclassify")String updateclassify,
                            @ModelAttribute("delcl")String delcl,
                             @ModelAttribute("addclassify")String addclassify){
        List<Classify> classifies = classifyMapper.selectList(null);
        model.addAttribute("classifies",classifies);

        //说实话，这里我觉得可以优化一下，用同一个值来做或许比较好？
        int classcurrent = -1;
        //更新判断
        if (updateclassify =="yes"){
            classcurrent =1;
        }else if (updateclassify ==null){
            classcurrent = 2;
        }
        //删除判断
        int delcurrent=-1;
        if (delcl =="yes"){
            delcurrent =1;
        }else if (delcl =="no"){
            delcurrent =2;
        }else if (delcl =="wu"){
            delcurrent =7;
        }
        //添加判断
        int addcurrent= -1;
        if (addclassify=="yes"){
            addcurrent =1;
        }else if (addclassify ==null){
            addcurrent =2;
        }


        model.addAttribute("classifyupdate",classcurrent);
        model.addAttribute("classifydel",delcurrent);
        model.addAttribute("classifyadd",addcurrent);
        return "classifyad/classifyadindex";
    }

    //添加分类
    @PostMapping("/addClassify")
    public String addClassify(Classify classify,RedirectAttributes ra){
        System.out.println(classify);
        int insert = classifyMapper.insert(classify);
        String addclassify;
        if (insert == 1){
            addclassify ="yes";
        }else {
            addclassify =null;
        }
        ra.addFlashAttribute("addclassify",addclassify);
        return "redirect:/toclassify";
    }

    //分类编辑
    @GetMapping("/toupdateClassify/{id}")
    public  String toupdateClassify(@PathVariable int id,Model model){
        //取出分类名字
        Classify classify = classifyMapper.selectById(id);
        model.addAttribute("classify",classify);

        //上面获取了博客的id之后，用于查找
//        QueryWrapper wrapper =new QueryWrapper();
//        wrapper.eq("bid",id);
//        Integer count = bcMapper.selectCount(wrapper);

        return "classifyad/classifyadupdate";
    }

    //更新保存
    @PostMapping("/updateclassify")
    public  String updateclassify(Classify classify, RedirectAttributes ra){

        String b = classifyMapper.checkclassifyname(classify.getClassifyname());
        String msg;
            if (b ==null){
                classifyMapper.updateById(classify);
                System.out.println("标签更新成功");
                msg ="yes";
            }else {
                System.out.println("有重复的地方或者其他原因");
                msg =null;
            }
        ra.addFlashAttribute("updateclassify",msg);

        return"redirect:/toclassify";
    }
    //删除好像没做？？
    @GetMapping("/delclassify")
    public String delclassify(int delclassifyid,RedirectAttributes ra){
        String delstring;
        if (delclassifyid ==9){
            System.out.println("默认无法删除");
            delstring ="wu";
        }else {
            //把博客的分类全部转化成默认类
            QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("classifyid",delclassifyid);
            List<Blog> list = blogservice.list(queryWrapper);
            for (int a=0 ;a<list.size();a++){
                //设置为默认值
                list.get(a).setClassifyid(9);
                classifyMapper.updateClassifynum(list.get(a).getId());
//                System.out.println("保存结果=>"+i);
            }
            System.out.println(list);
            int i = classifyMapper.deleteById(delclassifyid);

            if (i==1){
                delstring ="yes";
            }else {
                delstring="no";
            }
        }
        ra.addFlashAttribute("delcl",delstring);

        return "redirect:/toclassify";
    }

   
}
