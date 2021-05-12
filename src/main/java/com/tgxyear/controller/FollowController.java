package com.tgxyear.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tgxyear.mapper.FollowMapper;
import com.tgxyear.pojo.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FollowController {

    @Autowired
    FollowMapper followMapper;

    @PostMapping("/Followfun")
    @ResponseBody
    public int Followfun(int followed,int noticeid, int followid){
        if (followid==1){
            //用户关注了
            Follow follow =new Follow();
            follow.setFollowid(followed);
            follow.setNoticeid(noticeid);
            followMapper.insert(follow);
            System.out.println("follow=>"+follow);
        }else if (followid==0){
            followMapper.dellfollow(followed,noticeid);
        }

        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("noticeid",noticeid);
        Integer data = followMapper.selectCount(wrapper);
        return data;
    }
}
