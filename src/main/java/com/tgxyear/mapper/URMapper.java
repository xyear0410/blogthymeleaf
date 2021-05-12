package com.tgxyear.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxyear.pojo.User;
import com.tgxyear.pojo.User_role;
import org.springframework.stereotype.Repository;


@Repository
public interface URMapper extends BaseMapper<User_role> {

    int adduserroid(int id);
//分页
    IPage<User> selectPageVo(Page<?> page);
    //
    int selectcountid();


}
