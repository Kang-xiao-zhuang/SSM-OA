package com.zhuang.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhuang.model.system.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 角色菜单 Mapper 接口
 * </p>
 *
 * @author KangXiaoZhuang
 * @since 2023-03-06
 */
@Repository
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

}
