package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelItemDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = TravelItemService.class)
@Transactional
public class TravelItemServiceImpl implements TravelItemService {
    @Autowired
    TravelItemDao travelItemDao;
    public void add(TravelItem travelItem) {
          travelItemDao.add(travelItem);
    }

    public PageResult findPage(QueryPageBean queryPageBean) {
         //1. 使用PageHelper 进行分页 select * from t_travelItem where queryString=? limit ?,?
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());//limit?,?
        //2. 查询条件 queryString
        Page<TravelItem> page= travelItemDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    public void delete(Integer id) {
       long count= travelItemDao.selectTravelgroup_TravelitemByTravelIemId(id);
        if (count>0){
            throw new RuntimeException("自由行不能被删除");
        }else {
            travelItemDao.delete(id);
        }
    }

    public TravelItem findByid(Integer id) {
        return travelItemDao.findById(id);
    }

    public void edit(TravelItem travelItem) {
        travelItemDao.edit(travelItem);
    }

    public List<TravelItem> fingAll() {
        return travelItemDao.findAll();
    }
}
