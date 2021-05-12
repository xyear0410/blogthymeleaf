package com.tgxyear.pojo;


import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User  implements UserDetails, Serializable {

    @TableId(type = IdType.AUTO)
    private int id;
    private String username;
    private String password;
    private String name;
    private  int sex;
    private  String email;
    private String userpic;

    @Getter(value = AccessLevel.NONE)
    private Boolean enabled;
    private Boolean locked;
    private List<Role> roles;


    //用户与角色相匹配
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
//            System.out.println(role.getName().trim());
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//            System.out.println(authorities);
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int say(){
        if (enabled){
            return 1;
        }else {
            return 0;
        }
    }
    public Boolean getenable(){
        return enabled;
    }
}
