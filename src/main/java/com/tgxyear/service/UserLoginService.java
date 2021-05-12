package com.tgxyear.service;

import com.tgxyear.mapper.UserMapper;
import com.tgxyear.pojo.User;
import com.tgxyear.utils.ContextHolderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserLoginService  implements UserDetailsService {

    private String passwordParameter = "password";

    @Autowired
    UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, AuthenticationException {
        HttpServletRequest request = ContextHolderUtils.getRequest();
        String pwd = request.getParameter(passwordParameter);//前端获取

        User user = userMapper.loadUserByUserByUsername(username);//后端

        if (user == null) {
            throw new BadCredentialsException("账户不存在!");
        }
        if (!pwd.equals(user.getPassword())) {
            throw new BadCredentialsException("密码错误，请重新输入");
        }

        BCryptPasswordEncoder pwdencode = new BCryptPasswordEncoder();
        String encode = pwdencode.encode(user.getPassword().trim());
        user.setPassword(encode);
        user.setRoles(userMapper.getUserRolesByUid(user.getId()));
        return user;
    }
}