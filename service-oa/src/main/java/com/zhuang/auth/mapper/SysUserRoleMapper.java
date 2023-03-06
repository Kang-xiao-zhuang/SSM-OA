package com.zhuang.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhuang.model.system.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * description: SysUserRoleMapper
 * date: 2023/3/6 9:12
 * author: Zhuang
 * version: 1.0
 */
@Repository
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

}
