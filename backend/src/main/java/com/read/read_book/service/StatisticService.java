package com.read.read_book.service;

import com.read.read_book.dto.*;
import com.read.read_book.pojo.Userstatistic;
import org.springframework.stereotype.Service;

import java.util.List;
public interface StatisticService {
    public List<Commentstatic> commentstatic();//各类型的评价总数

    public List<Goodcommentstatic> goodcommentstatic();//各类型的好评总数

    public List<loginstastic> statisticloginweek();//统计一周内每一天的活跃用户


    public List<loginstatistic2> statisticmost();//统计活跃度前5的用户的用户名和活跃度（登录次数）

    public List<loginstastic3> monthstatistic();


}
