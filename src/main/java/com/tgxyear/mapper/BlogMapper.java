package com.tgxyear.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxyear.pojo.Blog;
import com.tgxyear.pojo.BlogVo;
import com.tgxyear.utils.BlogUser;
import com.tgxyear.utils.SearchUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogMapper extends BaseMapper<Blog>  {

    public List<Blog> getallmessage();

    //管理博客列表
    public List<BlogVo> getallblogusers(IPage<BlogVo> page);

    public void insertBlogadmin(Blog blog);

    public int getmaxBid();

    public String findima(int id);

    //截取部分字段
    public List<Blog> cutblogcontent();

    //更新点赞数
    void updateBgreatById(@Param("bgreat")int bgreat,@Param("bid") int bid);

    //自定义分页
    IPage<Blog> selectPageText(Page<Blog> page);

    //某人全部博客并分页
    IPage<Blog> selectonePageText(@Param("uid")int uid,Page<Blog> page);

    //封装
    BlogVo selectblogmsg(@Param("bid") int bid);

    //查找某人博客缩略版
    List<Blog> selectoneblog(@Param("uid")int uid);
    List<Blog> selectoneblog(@Param("uid")int uid,IPage<Blog> page);

    List<SearchUser> searchuserlike(@Param("text")String text);
    //管理员
    List<Blog> selectadpageblog(IPage<Blog> page,@Param("title") String title,@Param("name") String name);
        //数上面的值
    int countselectadpageblog(@Param("title") String title,@Param("name") String name);


    //动态 即关注的人和自己的博客显示
    List<Blog> actionblog(@Param("uid")int uid);

    //动态增强版
    List<BlogUser> actionbloguser(IPage<BlogUser> page, @Param("uid")int uid);
    //
    //上面的总数
    int countactionbloguser(@Param("uid")int uid);


}
