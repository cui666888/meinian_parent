package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    OrderSettingDao orderSettingDao;

    public void add(List<OrderSetting> orderSettingList) {
        if (orderSettingList!=null){
            for (OrderSetting orderSetting : orderSettingList) {
                long count=orderSettingDao.findOrderSettingByOrderDate(orderSetting.getOrderDate());
                if (count>0){
                    orderSettingDao.editOrderSettingByOrderDate(orderSetting);
                }else {
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    public List<Map> getOrderSettingByMonth(String date) {
        List<OrderSetting> orderSettings= orderSettingDao.getOrderSettingByMonth(date);
        List<Map> listMap =new ArrayList<Map>();
        for (OrderSetting orderSetting : orderSettings) {
            Map map=new HashMap();
            map.put("date", orderSetting.getOrderDate().getDate());
            map.put("number", orderSetting.getNumber());
            map.put("reservations", orderSetting.getReservations());
            listMap.add(map);
        }
        return listMap;
    }

    public void editNumberByDate(OrderSetting orderSetting) {
        OrderSetting orderSettingDB=orderSettingDao.findOrderSettingObjectByOrderDate(orderSetting.getOrderDate());
        if (orderSettingDB==null){
            orderSettingDao.add(orderSetting);
        }else{
            if (orderSettingDB.getReservations()>orderSetting.getNumber()){
                throw new RuntimeException("不能进行设置");
            }else {
                orderSettingDao.editOrderSettingByOrderDate(orderSetting);
            }
        }
    }
}
