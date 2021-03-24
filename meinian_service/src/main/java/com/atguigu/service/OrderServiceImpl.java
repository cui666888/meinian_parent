package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService  {
    @Autowired
    OrderSettingDao orderSettingDao;
    @Autowired
    MemberDao memberDao;
    @Autowired
    OrderDao orderDao;
    public Result add(Map map) throws Exception {
        Date date = DateUtils.parseString2Date(map.get("orderDate").toString());
        OrderSetting orderSetting = orderSettingDao.findOrderSettingObjectByOrderDate(date);
        if (orderSetting==null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }else {
            int number = orderSetting.getNumber();
            int reservations = orderSetting.getReservations();
            if (reservations>=number){
                return new Result(false, MessageConstant.ORDER_FULL);
            }else {
                String telephone =map.get("telephone").toString();
                Member member=memberDao.getMemberByTelephone(telephone);
                if (member!=null){
                    Map param =new HashMap();
                    param.put("member_id", member.getId());
                    param.put("orderDate", date);
                    param.put("setmeal_id", map.get("setmealId"));
                    Order order= orderDao.getOrder(param);
                    if (order!=null){
                        return new Result(false, MessageConstant.HAS_ORDERED);
                    }
                }else {
                    member=new Member();
                    member.setName(map.get("name").toString());
                    member.setSex(map.get("sex").toString());
                    member.setIdCard(map.get("sex").toString());
                    member.setRegTime(new Date());
                    member.setPhoneNumber(telephone);
                    memberDao.add(member);
                }
                orderSetting.setReservations(orderSetting.getReservations()+1);
                orderSettingDao.editReservations(orderSetting);
                Order order =new Order();
                order.setOrderStatus(order.ORDERSTATUS_NO);
                order.setOrderType("微信预约");
                order.setMemberId(member.getId());
                order.setSetmealId(Integer.parseInt(map.get("setmealId").toString()));
                order.setOrderDate(date);
                orderDao.add(order);
                return new Result(true, MessageConstant.ORDER_SUCCESS, order);
            }
        }

    }

    public Map findById(Integer id) throws Exception {
        Map map=orderDao.findById(id);
        Date date= (Date) map.get("orderDate");
        String orderDate1 = DateUtils.parseDate2String(date);
        map.put("orderDate", orderDate1);

        return map;
    }
}
