package com.macro.mall.tiny.service;

import com.macro.mall.tiny.dto.AdminUserDetails;
import com.macro.mall.tiny.dto.UmsResource;

import java.util.List;


public interface UmsAdminService {
    /**
     * 根据用户名获取用户信息
     */
    AdminUserDetails getAdminByUsername(String username);

    /**
     * 获取所以权限列表
     */
    List<UmsResource> getResourceList();

    /**
     * 用户名密码登录
     */
    String login(String username, String password);
}
