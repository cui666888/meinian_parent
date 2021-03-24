package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.service.MemberService;
import com.atguigu.service.ReportService;
import com.atguigu.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportMemberController {
    @Reference
    MemberService memberService;
    @Reference
    SetmealService setmealService;
    @Reference
    ReportService reportService;


    @RequestMapping("/getMemberReport.do")
    public Result getMemberReport(){
        Map<String,Object> map=new HashMap<String, Object>();
        List<String> months=new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);//获取当前日期的前第12个月的日期
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1);
            months.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
        }
        map.put("months",months);//前12个月月份
        List<Integer> memberCount=memberService.getMemberCountByMonth(months);
        map.put("memberCount",memberCount);//前12个月的会员数量

        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }
    @RequestMapping("/getSetmealReport.do")
    public Result getSetmealReport(){
        List<Map> listMap =setmealService.querySetmeal();
        Map<String,Object> map =new HashMap<String, Object>();
        List<String> setmealNames=new ArrayList<String>();
        for (Map map1 : listMap) {
            String setmealName = map1.get("name").toString();
            setmealNames.add(setmealName);
        }
        map.put("setmealNames", setmealNames);
        map.put("setmealCount", listMap);
        return new Result(true, "统计套餐的占比成功", map);
    }
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        Map map=reportService.getBusinessReportData();
        return new Result(true, "查询运营数据成功", map);

    }

}
