package com.atguigu.controller;

import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    JedisPool jedisPool;
    @RequestMapping("/check.do")
    public Result check(@RequestBody Map map){
        String telephone=map.get("telephone").toString();
        String validateCode = map.get("validateCode").toString();
        String redisCode=jedisPool.getResource().get(telephone+ RedisMessageConstant.SENDTYPE_LOGIN);
        if (redisCode!=null&&redisCode.equals(validateCode)){
            return new Result(true, "登录成功");
        }else {
            return new Result(false, "登录失败");
        }

    }

}
