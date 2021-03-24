package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;

import java.util.List;

public interface TravelgroupService {
    void add(Integer[] travelItemIds, TravelGroup travelGroup);

    PageResult findPage(QueryPageBean queryPageBean);

    TravelGroup findTravelgroup(Integer id);

    List<Integer> findTravelItemIds(Integer id);

    void edit(Integer[] travelItemIds, TravelGroup travelGroup);

    void deleteById(Integer id);

    List<TravelGroup> findAll();
}
