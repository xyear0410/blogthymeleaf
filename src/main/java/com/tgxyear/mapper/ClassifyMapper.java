package com.tgxyear.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.tgxyear.pojo.Classify;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassifyMapper extends BaseMapper<Classify> {
    Classify selectByid(@Param("bid")int bid);
    String checkclassifyname (@Param("checkclassifyname")String checkclassifyname);
    void updateClassifynum(@Param("id")int id);
}
