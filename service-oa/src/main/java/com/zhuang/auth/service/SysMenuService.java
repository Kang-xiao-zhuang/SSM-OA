package com.zhuang.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuang.model.system.SysMenu;
import com.zhuang.vo.system.AssginMenuVo;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author KangXiaoZhuang
 * @since 2023-03-06
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 菜单树形数据
     *
     * @return
     */
    List<SysMenu> findNodes();

    /**
     * 根据角色获取授权权限数据
     * @return
     */
    List<SysMenu> findSysMenuByRoleId(Long roleId);

    /**
     * 保存角色权限
     * @param  assginMenuVo
     */
    void doAssign(AssginMenuVo assginMenuVo);
}
