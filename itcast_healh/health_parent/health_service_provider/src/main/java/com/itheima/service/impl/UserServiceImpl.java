package com.itheima.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.*;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.*;
import com.itheima.service.StemealService;
import com.itheima.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 检查组项服务
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

   //查询这个人的角色和权限
    public User findByUserName(String username) {
        User user=null;
        user= userDao.findByUserName(username);
        //根据用户ID查询对应的角色

        if (user!=null){
            Set<Role> roles= roleDao.findByUid(user.getId());

            for (Role role : roles) {
                //根据角色ID查询关联的权限

                Set<Permission> permissions=permissionDao.findByRid(role.getId());
                role.setPermissions(permissions);//让角色关联权限

            }
            user.setRoles(roles);//让用户关联角色

        }


        return user;
    }
}
