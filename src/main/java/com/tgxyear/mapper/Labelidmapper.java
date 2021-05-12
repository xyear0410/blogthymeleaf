package com.tgxyear.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tgxyear.pojo.Blog;
import com.tgxyear.pojo.Labelid;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Labelidmapper extends BaseMapper<Labelid> {
    List<Blog> getbattle(@Param("lid")int lid);
}
