package com.tgxyear.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tgxyear.pojo.Labelview;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelViewMapper extends BaseMapper<Labelview> {
 List<Labelview> getlid(@Param("uid")int uid);
}
