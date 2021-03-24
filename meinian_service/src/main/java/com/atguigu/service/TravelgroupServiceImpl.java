package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelItemDao;
import com.atguigu.dao.TravelgroupDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = TravelgroupService.class)
@Transactional
public class TravelgroupServiceImpl implements TravelgroupService {
    @Autowired
    TravelgroupDao travelgroupDao;
    @Autowired
    TravelItemDao travelItemDao;
    public void add(Integer[] travelItemIds, TravelGroup travelGroup) {
        travelgroupDao.add(travelGroup);
        insert_travelGroup_travelItem(travelGroup.getId(),travelItemIds);
    }

    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<TravelGroup> page=travelgroupDao.findPage(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),page.getResult());
    }

    public TravelGroup findTravelgroup(Integer id) {
        return travelgroupDao.findTravelgroup(id);
    }

    public List<Integer> findTravelItemIds(Integer id) {
        return travelgroupDao.findTravelItemIds(id);
    }

    public void edit(Integer[] travelItemIds, TravelGroup travelGroup) {
        travelgroupDao.edit(travelGroup);
        travelgroupDao.delete(travelGroup.getId());
        insert_travelGroup_travelItem(travelGroup.getId(),travelItemIds);
    }

    public void deleteById(Integer id) {
        long count= travelItemDao.selectTravelgroup_TravelitemByTravelIemId(id);
        if (count>0){
            throw new RuntimeException("抱团游不能被删除");
        }else {
            travelgroupDao.deleteById(id);
        }
    }

    public List<TravelGroup> findAll() {
        return travelgroupDao.findAll();
    }

    public void insert_travelGroup_travelItem(Integer travelGroupId,Integer[] travelItemIds){
        for (Integer travelItemId : travelItemIds) {
            Map map=new HashMap();
            map.put("travelGroupId",travelGroupId);
            map.put("travelItemId",travelItemId);
            travelgroupDao.insert_travelGroup_travelItem(map);
        }
    }
}
