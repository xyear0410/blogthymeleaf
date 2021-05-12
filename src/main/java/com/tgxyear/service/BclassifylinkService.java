package com.tgxyear.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tgxyear.pojo.B_clink;

import java.util.List;

public interface BclassifylinkService extends IService<B_clink> {
    public List<String> getblogoneclassify(int id);
}
