package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController  //作用 : 包含两个注解 @ResponseBody  @Controller
@RequestMapping("/travelItem")
public class TravelItemController {
     @Reference
    TravelItemService travelItemService;
    @PreAuthorize("hasAuthority('TRAVELITEM_ADD')")
    @RequestMapping("/add.do")
    public Result add(@RequestBody TravelItem travelItem){
        try {
            travelItemService.add(travelItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
    }
    //分页查询 查询结果返回pageResult
    @PreAuthorize("hasAuthority('TRAVELITEM_QUERY')")
    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult= travelItemService.findPage(queryPageBean);
        return pageResult;
    }
    @PreAuthorize("hasAuthority('abc')")
    @RequestMapping("/delete.do")
    public Result delete(Integer id){
        try {
            travelItemService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_TRAVELITEM_SUCCESS);
    }
    @RequestMapping("/findById.do")
    public Result findById(Integer id){
        TravelItem travelItem= travelItemService.findByid(id);
        return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelItem);
    }
    @RequestMapping("/edit.do")
    public Result edit(@RequestBody TravelItem travelItem){
        try {
            travelItemService.edit(travelItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_TRAVELITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_TRAVELITEM_SUCCESS);
    }
    @RequestMapping("/findAll.do")
    public Result findAll(){
        List<TravelItem> travelItems =travelItemService.fingAll();
        return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelItems);
    }

}
