package com.atguigu.dao;

import com.atguigu.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {

    long findOrderSettingByOrderDate(Date orderDate);

    void editOrderSettingByOrderDate(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(String date);

    OrderSetting findOrderSettingObjectByOrderDate(Date orderDate);

    void editReservations(OrderSetting orderSetting);
}
