package com.atguigu.dao;

import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface TravelgroupDao {

    void add(TravelGroup travelGroup);

    void insert_travelGroup_travelItem(Map map);

    Page<TravelGroup> findPage(String queryString);

    TravelGroup findTravelgroup(Integer id);

    List<Integer> findTravelItemIds(Integer id);

    void edit(TravelGroup travelGroup);

    void delete(Integer id);

    void deleteById(Integer id);

    List<TravelGroup> findAll();

    List<TravelGroup> findTravegroupsBySetmealId(Integer setMealid);
}
