package com.tgxyear.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tgxyear.pojo.Label;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Labelmapper extends BaseMapper<Label> {
    List<Label> getbloglabel(@Param("bid")int bid);
}
