package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    JedisPool jedisPool;
    @Reference
    OrderService orderService;
    @RequestMapping("/submit.do")
    public Result submit(@RequestBody Map map){
        String telephone = map.get("telephone").toString();
        String validateCode = map.get("validateCode").toString();
        String code = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        if (code==null||!code.equals(validateCode)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }else {
            Result result =null;
            try {
                result =orderService.add(map);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, MessageConstant.ORDER_FAIL);
            }
            return result;
        }
    }

    @RequestMapping("/findById.do")
    public Result findById(Integer id) throws Exception {
        Map map=orderService.findById(id);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
    }
}
