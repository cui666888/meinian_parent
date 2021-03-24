package com.atguigu.dao;

import com.atguigu.pojo.Role;

import java.util.List;

public interface RoleDao {
    public List<Role> queryRoleByUserId(Integer roleId);
}
