package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {
    @Autowired
     private   BCryptPasswordEncoder bCryptPasswordEncoder;

    @Reference
    private  UserService userService;


//    实现从数据库查询数据,查某个用户的角色和权限
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);
        if (user == null) {
            return null;

        }

        Set<Role> roles = user.getRoles();
        ArrayList<GrantedAuthority> list = new ArrayList<>();

        for (Role role : roles) {
//               给用户授予角色
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
//                   给用户授予权限
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }

        }
//        将密码加密
        //如果数据库中的密码是没有加密的,启用了加密的框架,从数据库查出来的密码直接返回给框架就会导致登录不成功
       // String encode = bCryptPasswordEncoder.encode(user.getPassword());

        org.springframework.security.core.userdetails.User securityUser= new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);
        return securityUser;
    }
}
