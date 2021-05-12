package com.tgxyear.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tgxyear.pojo.Comment;

import java.util.List;

public interface Commentservice extends IService<Comment> {
    public List<Comment> getblogonecomment(int id);

    //查询评论列表
    List<Comment> listComment(int bid);
    //添加一个评论
    int saveComment(Comment comment);

}
