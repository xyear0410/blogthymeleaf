package com.tgxyear.utils;

import lombok.Data;

import java.util.Date;

@Data
public class Commentvo {

    //需要什么？

    //该博客链接 --这个后面可以调用
    //博客id ---博客标题
    //评论作者
    //评论内容
    //博客创造时间
    private int id;
    private int uid;
    private String uname;
    private String content;
    private int bid;
    private String title;
    private Date createtime;
    private int check;

}
