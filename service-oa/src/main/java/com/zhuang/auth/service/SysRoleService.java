package com.zhuang.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuang.model.system.SysRole;
import com.zhuang.vo.system.AssginRoleVo;

import java.util.Map;

/**
 * description: SysRoleService
 * date: 2023/3/1 20:12
 * author: Zhuang
 * version: 1.0
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 根据用户获取角色数据
     * @param userId
     * @return
     */
    Map<String, Object> findRoleByUserId(Long userId);

    /**
     * 分配角色
     * @param assginRoleVo
     */
    void doAssign(AssginRoleVo assginRoleVo);
}