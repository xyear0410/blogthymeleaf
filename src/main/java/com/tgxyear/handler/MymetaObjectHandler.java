package com.tgxyear.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MymetaObjectHandler implements MetaObjectHandler {
    //插入时填充
    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("时间开始插入");
        this.setFieldValByName("createtime",new Date(),metaObject);
        this.setFieldValByName("updatetime",new Date(),metaObject);
        System.out.println("时间插入完成");
    }
    //更新时填充
    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("开始更新时间");
        this.setFieldValByName("updatetime",new Date(), metaObject);
        System.out.println("时间更新完毕");
    }
}
