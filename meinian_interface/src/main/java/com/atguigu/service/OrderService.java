package com.atguigu.service;

import com.atguigu.entity.Result;

import java.util.Map;

public interface OrderService {
    Result add(Map map) throws Exception;

    Map findById(Integer id) throws Exception;
}
