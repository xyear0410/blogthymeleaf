package com.tgxyear.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Comment {
    private int id;
    private String content;
    private  int great;
    private int uid;
    private String uname;
    private  int parentCommentId;
    private int bid;
    private String avater;

//    时间
    @TableField(fill = FieldFill.INSERT)

    private Date createtime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatetime;


    //评论回复
    private List<Comment> replyComments = new ArrayList<>();
    private Comment parentComment;
    private String parentNickname;


}
