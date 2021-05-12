package com.tgxyear.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tgxyear.mapper.CommentMapper;

import com.tgxyear.mapper.UserMapper;
import com.tgxyear.pojo.Comment;
import com.tgxyear.service.Commentservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class Commentserviceimpl extends ServiceImpl<CommentMapper, Comment> implements Commentservice {
   @Autowired
   CommentMapper commentMapper;

   @Autowired
    UserMapper userMapper;

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    @Override
    public List<Comment> getblogonecomment(int id) {
        return commentMapper.getblogonecomment(id);
    }

    @Override
    public List<Comment> listComment(int bid) {
        //查询所有的父评论
        List<Comment> listcomment = commentMapper.findtwo(bid,-1);
        for(Comment comment : listcomment) {
            //父评论id ，名字
            int id =comment.getId();
            String parentNickname1 =comment.getUname();
            List<Comment> childComments = commentMapper.findByParentIdNotNull(id);
            //查询出子评论,调用方法
            combineChildren(childComments, parentNickname1);
            comment.setReplyComments(tempReplys);
            //清空
            tempReplys = new ArrayList<>();
        }
        return listcomment;
    }



    /**
     * @Description: 查询出子评论
     * @Param: childComments：所有子评论
     * @Param: parentNickname1：父评论的姓名
     * @Return:
     */
    private void combineChildren(List<Comment> childComments, String parentNickname1) {
        //判断是否有一级子回复
        if(childComments.size() > 0){
            //循环找出子评论的id
            for(Comment childComment : childComments){
                String parentNickname = childComment.getUname();
                childComment.setParentNickname(parentNickname1);
                tempReplys.add(childComment);
               int childId = childComment.getId();
                //查询二级以及所有子集回复
                recursively(childId, parentNickname);
            }
        }
    }


    /**
     * @Description: 循环迭代找出子集回复
     * @Param: childId：子评论的id
     * @Param: parentNickname1：子评论的姓名
     * @Return:
     */
    private void recursively(int childId, String parentNickname1) {
        //根据子一级评论的id找到子二级评论
        List<Comment> replayComments = commentMapper.findByReplayId(childId);

        if(replayComments.size() > 0){
            for(Comment replayComment : replayComments){
                String parentNickname = replayComment.getUname();
                replayComment.setParentNickname(parentNickname1);
                int replayId = replayComment.getId();
                tempReplys.add(replayComment);
                //循环迭代找出子集回复
                recursively(replayId,parentNickname);
            }
        }
    }

//保存
    @Override
    public int saveComment(Comment comment) {
        comment.setCreatetime(new Date());
        comment.setUpdatetime(new Date());
        System.out.println("id=>"+comment.getUid());
        System.out.println("output=>"+userMapper.getuserphoto(comment.getUid()));
        comment.setAvater(userMapper.getuserphoto(comment.getUid()));
        System.out.println("当前时间"+comment.getCreatetime());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        sdf.format(comment.getCreatetime());
//        System.out.println(sdf);
   String timeStr1=   LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return commentMapper.saveComment(comment);
    }
}
