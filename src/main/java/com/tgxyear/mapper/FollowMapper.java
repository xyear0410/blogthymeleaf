package com.tgxyear.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tgxyear.pojo.Follow;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowMapper extends BaseMapper<Follow> {
    int dellfollow(@Param("followid")int followid,@Param("noticeid") int noticeid);
}
