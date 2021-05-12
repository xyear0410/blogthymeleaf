package com.tgxyear.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxyear.pojo.Blog;
import com.tgxyear.pojo.UBlink;
import com.tgxyear.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UBlinkMapper  extends BaseMapper<UBlink> {
    //获取具体博客信息
    List<UBlink> getalls();
    //   <!--获取uid的值，用来获取用户信息-->
    int  getuID(int id);
    //获取所有用户信息
    List<User> getalluser();
    //分页
    IPage<UBlink> selectPageText(Page<UBlink> page);

    boolean insertub(@Param("uid")int uid,@Param("bid") int bid);

}
