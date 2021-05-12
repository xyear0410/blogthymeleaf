package com.tgxyear.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tgxyear.pojo.Comment;
import com.tgxyear.utils.Commentvo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper extends BaseMapper<Comment> {
    public List<Comment> getblogonecomment(int id);

    //添加一个评论
    int saveComment(Comment comment);
    //查询父级评论
    List<Comment> findByParentIdNull(@Param("ParentId") int ParentId);
    //查询一级回复
    List<Comment> findByParentIdNotNull(@Param("id") int id);
    //查询二级以及所有子集回复
    List<Comment> findByReplayId(@Param("childId") int childId);

    //根据博客id
    List<Comment>  findtwo(@Param("Bid")int bid,@Param("ParentId") int ParentId);

    //评论别表--管理员
    List<Comment> findallcomment(IPage<Comment> page);

    //具体的某一个评论 --管理员
    Commentvo findOneById(@Param("id")int id);

    //修改
    int updateContentById(@Param("content")String content,@Param("commentid")int commentid);

    //用户评论分页
    List<Comment> selectusercomment( IPage<Comment> page,@Param("uid")int uid);

    //判断是否存在改博客，用于管理员的
    boolean checkexitblogcomment(@Param("bid") int id);
}
