package com.zhuang.auth.service.impl;

import com.zhuang.auth.service.SysMenuService;
import com.zhuang.auth.service.SysUserService;
import com.zhuang.model.system.SysUser;
import com.zhuang.security.custom.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * description: UserDetailsServiceImpl
 * date: 2023/3/12 22:43
 * author: Zhuang
 * version: 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService { //改为 org.springframework.security.core.userdetails.UserDetails;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 根据用户名查询
        SysUser sysUser = sysUserService.getByUsername(username);
        if(null == sysUser) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        if(sysUser.getStatus().intValue() == 0) {
            throw new RuntimeException("账号已停用");
        }

        // 根据 user_id 查询用户操作权限数据
        List<String> userPermsList = sysMenuService.findUserPermsByUserId(sysUser.getId());
        // 创建list集合，封装最终权限数据
        List<SimpleGrantedAuthority> authList =  new ArrayList<>();
        // 遍历 authList
        for (String perms : userPermsList) {
            authList.add(new SimpleGrantedAuthority(perms.trim()));
        }

        return new CustomUser(sysUser, authList);
    }
}
