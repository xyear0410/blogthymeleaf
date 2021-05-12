package com.tgxyear.controller;


import com.tgxyear.mapper.PhotoMapper;
import com.tgxyear.mapper.UpicMapper;
import com.tgxyear.pojo.Upic;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Controller
public class UipcController {

    @Autowired
    UpicMapper upicMapper;

    @Autowired
    PhotoMapper photoMapper;

    @SneakyThrows
    @PostMapping("/updatepersonbackground")
    public String updatepersonbackground(Upic upic,@RequestParam(value = "file") MultipartFile file){
        System.out.println("更改图片的"+upic);
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
            System.out.println("文件不为空");
        } else {
            System.out.println("文件为空");
        }
        sqlPath = "/image/" + filename;
        System.out.println("文件名字"+filename);
        System.out.println(sqlPath);
        upic.setPic(sqlPath);
        upic.setId(upic.getUid());
        System.out.println("得到的行数"+upic);
        int i = upicMapper.updateById(upic);
        System.out.println("修改行数"+i);

        return "redirect:/topersonpage/"+upic.getUid();
    }


    @ResponseBody
    @GetMapping("/getpic/{id}")
    public String getpic(@PathVariable("id")int id){
        //上面是用户id
        photoMapper.selectById(id).getPhoto();

        return  photoMapper.selectById(id).getPhoto();
    }

}
