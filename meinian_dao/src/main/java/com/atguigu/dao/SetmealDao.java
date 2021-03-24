package com.atguigu.dao;

import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    void insert_setmeal_travelgroup(Map map);

    void add(Setmeal setmeal);

    Page<Setmeal> findPage(String queryString);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);

    Setmeal getSetmealById(Integer id);

    List<Map> querySetmeal();
}
