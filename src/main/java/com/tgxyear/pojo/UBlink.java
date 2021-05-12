package com.tgxyear.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class UBlink {

    private int id;
    private int uid;
    private int bid;
    private String title;
    private String name;
    private String content;
    private String picture;
    private int view;
    private int bgreat;
    private Date createtime;
    private Date updatetime;

}
