package com.zhuang.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuang.auth.mapper.SysRoleMapper;
import com.zhuang.auth.service.SysRoleService;
import com.zhuang.model.system.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: SysRoleServiceImpl
 * date: 2023/3/1 20:13
 * author: Zhuang
 * version: 1.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
}
