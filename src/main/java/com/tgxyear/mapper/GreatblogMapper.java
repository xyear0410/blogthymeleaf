package com.tgxyear.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tgxyear.pojo.Blog;
import com.tgxyear.pojo.Greatblog;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GreatblogMapper extends BaseMapper<Greatblog> {
    int delall(@Param("bid") int bid, @Param("uid") int uid);
    int selectblogonegreat(@Param("blogid") int blogid);
    //判断是否点赞
    int checkgreat(@Param("bid")int bid,@Param("uid")int uid);

    //根据用户id来查询已点赞过的博客
    List<Blog> selectgreatblog(IPage<Blog> page, @Param("uid")int uid);

    //获取点赞数
    int getusergreat(@Param("uid")int id);

    //点赞数最高得倒叙


}
