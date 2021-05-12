package com.tgxyear.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tgxyear.mapper.BlogMapper;
import com.tgxyear.mapper.FollowMapper;
import com.tgxyear.mapper.GreatblogMapper;
import com.tgxyear.mapper.UserMapper;
import com.tgxyear.pojo.Blog;
import com.tgxyear.pojo.Follow;
import com.tgxyear.pojo.Greatblog;
import com.tgxyear.service.Blogservice;
import com.tgxyear.pojo.BlogVo;
import com.tgxyear.utils.SearchUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Blogserviceimpl extends ServiceImpl<BlogMapper, Blog> implements Blogservice {


    @Autowired
    BlogMapper blogMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    FollowMapper followMapper;
    @Autowired
    GreatblogMapper greatblogMapper;

    @Override
    public List<Blog> getallmessage() {
        List<Blog> getmessage = blogMapper.getallmessage();
        return getmessage;
    }

    @Override
    public IPage<Blog> selectPageText(Page<Blog> page) {
        return blogMapper.selectPageText(page);
    }

    @Override
    public IPage<Blog> selectonePageText(int uid, Page<Blog> page) {
        return blogMapper.selectonePageText(uid,page);
    }

    @Override
    public List<SearchUser> selectuser(String text) {
        List<SearchUser> searchuserlike = blogMapper.searchuserlike(text);
        for (int i = 0; i < searchuserlike.size(); i++) {
            searchuserlike.get(i).getId();//user的id
            //查询粉丝数
            QueryWrapper<Follow> wrapper = new QueryWrapper<>();
            wrapper.eq("noticeid",searchuserlike.get(i).getId());
            followMapper.selectCount(wrapper);
            searchuserlike.get(i).setAllnoticenum(followMapper.selectCount(wrapper));
            System.out.println("zong=>"+followMapper.selectCount(wrapper));
            //查询点赞数
            QueryWrapper<Greatblog> wrapper1 =new QueryWrapper<>();
            wrapper1.eq("uid",searchuserlike.get(i).getId());
            greatblogMapper.selectCount(wrapper1);
            searchuserlike.get(i).setAllgreat(greatblogMapper.selectCount(wrapper1));
            System.out.println(searchuserlike.get(i));
            //查询关注数
        }

        return searchuserlike;
    }


    //更新弹窗时,弹出的信息



}
