package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;
import com.atguigu.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    SetmealService setmealService;
    @Autowired
    JedisPool jedisPool;
    @RequestMapping("/upload.do")
    public Result upload(MultipartFile imgFile) throws IOException {
        String filename = imgFile.getOriginalFilename();
        String suffixName=filename.substring(filename.lastIndexOf("."));
        filename= UUID.randomUUID().toString()+suffixName;
        QiniuUtils.upload2Qiniu(imgFile.getBytes(),filename);

        Jedis jedis = jedisPool.getResource();
        jedis.sadd(RedisConstant.SETMEAL_PIC_RESOURCES, filename);
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,filename);
    }
    @RequestMapping("/add.do")
    public Result add(Integer[] travelgroupIds, @RequestBody Setmeal setmeal){

        try {
            setmealService.add(travelgroupIds,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }
    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult=setmealService.findPage(queryPageBean);
        return pageResult;
    }
}
