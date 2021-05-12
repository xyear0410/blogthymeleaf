package com.tgxyear.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Blog implements Serializable {

    private  int id;
    private String title;
    private String content;
    private String picture;
    private int view;
    private int classifyid;
    private int uid;
    private int bgreat;
    private int byperson;
    private String url;

    @TableField(fill = FieldFill.INSERT)
    private Date createtime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatetime;

}
