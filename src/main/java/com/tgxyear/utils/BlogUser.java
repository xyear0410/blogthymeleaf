package com.tgxyear.utils;

import lombok.Data;

import java.util.Date;

@Data
public class BlogUser {

    private  int id;
    private String title;
    private String content;
    private String picture;
    private int view;
    private int classifyid;
    private int uid;
    private int bgreat;
    private String name;
    private String userpic;
    private Date createtime;
    private Date updatetime;

}
