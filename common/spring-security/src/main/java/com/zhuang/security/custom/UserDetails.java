package com.zhuang.security.custom;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * description: UserDetails
 * date: 2023/3/11 23:16
 * author: Zhuang
 * version: 1.0
 */
public interface UserDetails extends Serializable {
    /**
     * 用户权限集合（这个权限对象现在不管它，到权限时我会讲解）
     */
    Collection<? extends GrantedAuthority> getAuthorities();
    /**
     * 用户密码
     */
    String getPassword();
    /**
     * 用户名
     */
    String getUsername();
    /**
     * 用户没过期返回true，反之则false
     */
    boolean isAccountNonExpired();
    /**
     * 用户没锁定返回true，反之则false
     */
    boolean isAccountNonLocked();
    /**
     * 用户凭据(通常为密码)没过期返回true，反之则false
     */
    boolean isCredentialsNonExpired();
    /**
     * 用户是启用状态返回true，反之则false
     */
    boolean isEnabled();
}
