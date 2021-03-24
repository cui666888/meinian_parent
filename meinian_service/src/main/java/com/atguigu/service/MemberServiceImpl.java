package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.MemberDao;
import com.atguigu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;
    public List<Integer> getMemberCountByMonth(List<String> months) {
        List<Integer> memberCount=new ArrayList<Integer>();
        for (String month : months) {//2021-03%  最后一天31
            String lastDayOfMonth = DateUtils.getLastDayOfMonth(month);//2021-03-31
            Integer count= memberDao.getMemberCountByMonth(lastDayOfMonth);
            memberCount.add(count);
        }
        return memberCount;
    }
}
