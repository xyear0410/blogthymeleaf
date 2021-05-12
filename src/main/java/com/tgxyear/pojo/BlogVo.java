package com.tgxyear.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.tgxyear.pojo.Blog;
import lombok.Data;

import java.util.Date;

@Data
public class BlogVo {
    private  int id;
    private String title;
    private String content;
    private String picture;
    private int view;
    private int classifyid;
    private int uid;
    private String name;
    private int bgreat;
    private String classifyname;
    private Date createtime;
    private Date updatetime;
}
