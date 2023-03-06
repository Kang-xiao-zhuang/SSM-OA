package com.zhuang.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhuang.model.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author KangXiaoZhuang
 * @since 2023-03-06
 */
@Repository
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}
