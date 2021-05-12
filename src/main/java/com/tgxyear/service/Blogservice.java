package com.tgxyear.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tgxyear.pojo.Blog;
import com.tgxyear.pojo.BlogVo;
import com.tgxyear.utils.SearchUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Blogservice  extends IService<Blog> {
    //获取博客信息
    public List<Blog> getallmessage();

    //分页
    IPage<Blog> selectPageText(Page<Blog> page);

    //
    IPage<Blog> selectonePageText(@Param("uid")int uid, Page<Blog> page);

    List<SearchUser> selectuser(String text);


}
