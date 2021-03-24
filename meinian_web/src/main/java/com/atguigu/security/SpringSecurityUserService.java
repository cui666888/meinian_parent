package com.atguigu.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.pojo.Permission;
import com.atguigu.pojo.Role;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {
    @Reference
    UserService userService;
    //从数据库中验证用户登录
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.根据用户名查询用户(user)
        User user =userService.queryUserByUserName(username);
        if(user!=null){
            String password=user.getPassword();
            //设置权限
            List<GrantedAuthority> list=new ArrayList<GrantedAuthority>();
            //2.根据用户的id查询角色的信息
            Set<Role> roles = user.getRoles();
            //3.根据角色的id查询对应的权限
            for (Role role : roles) {
                Set<Permission> permissions = role.getPermissions();
                for (Permission permission : permissions) {
                    list.add(new SimpleGrantedAuthority(permission.getKeyword()));
                }
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(),password,list);
        }


        return null;
    }
}
