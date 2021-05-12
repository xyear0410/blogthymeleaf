package com.tgxyear.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tgxyear.pojo.Role;
import com.tgxyear.pojo.User;
import com.tgxyear.utils.SearchUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

    User loadUserByUserByUsername(String username);

    List<Role> getUserRolesByUid(Integer id);

    Integer getoneUserid(String name);
    //根据id查User
    String getUsername(int id);
    //评论名字
    List<User> getcommentname(int id);

    //查询
    User selectoneuser(@Param("uid") int uid);

    boolean updateuser(User user);

    String getuserphoto(@Param("uid")int uid);

    //用户查找名字
    List<User> selectpageuser(IPage<User> page, @Param("name") String name);

    //上面的总数
    int countselectpageuser(@Param("name") String name);

    //根据用户名和姓名查找 管理员查找 名字 、 账号名
    List<User> selectadpageuser(IPage<User> page,
                                @Param("name") String name,
                                @Param("username")String username);

    //根据上面的 ，来查找总数量，用于分页
    int countadpageuser(@Param("name") String name,
                        @Param("username")String username);

    int  updateallById(User user);
    String getpassword(@Param("uid") int id);

    //获取用户数据 注意这里security保存之前的值
    User getmyusermsg(@Param("uid")int uid);
}
