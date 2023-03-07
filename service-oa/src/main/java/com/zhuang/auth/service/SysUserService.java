package com.zhuang.auth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuang.model.system.SysUser;

/**
 * description: SysUserService
 * date: 2023/3/6 20:53
 * author: Zhuang
 * version: 1.0
 */
public interface SysUserService extends IService<SysUser> {

    void updateStatus(Long id, Integer status);

    SysUser getByUsername(String username);
}
