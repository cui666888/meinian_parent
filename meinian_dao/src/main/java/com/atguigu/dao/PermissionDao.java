package com.atguigu.dao;

import com.atguigu.pojo.Permission;

import java.util.List;

public interface PermissionDao {
    public List<Permission> queryPermissionDaoByRoleId(Integer roleId);
}
