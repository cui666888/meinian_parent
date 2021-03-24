package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.RedisConstant;
import com.atguigu.dao.SetmealDao;
import com.atguigu.dao.TravelgroupDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    SetmealDao setmealDao;
    @Autowired
    JedisPool jedisPool;
    public void add(Integer[] travelgroupIds, Setmeal setmeal) {
        setmealDao.add(setmeal);
        insert_setmeal_travelgroup(setmeal.getId(), travelgroupIds);

        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
    }

    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Setmeal> page=setmealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    public List<Setmeal> getSetmeal() {
        return setmealDao.getSetmeal();
    }

    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    public Setmeal getSetmealById(Integer id) {
        return setmealDao.getSetmealById(id);
    }

    public List<Map> querySetmeal() {
        return setmealDao.querySetmeal();
    }

    public void insert_setmeal_travelgroup(Integer setmealId,Integer[] travelgroupIds){
        for (Integer travelgroupId : travelgroupIds) {
            Map map=new HashMap();
            map.put("setmealId", setmealId);
            map.put("travelgroupId", travelgroupId);
            setmealDao.insert_setmeal_travelgroup(map);
        }
    }
}
