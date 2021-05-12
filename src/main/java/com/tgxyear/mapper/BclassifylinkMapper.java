package com.tgxyear.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tgxyear.pojo.B_clink;
import com.tgxyear.pojo.Classify;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BclassifylinkMapper extends BaseMapper<B_clink> {
    List<Classify> getblogclassifyid(@Param("bid") int bid);
}
