package com.read.read_book.controller;

import com.read.read_book.common.Result;
import com.read.read_book.service.CommentService;
import com.read.read_book.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticController {
    @Autowired
    StatisticService statisticService;
   @GetMapping("/all")//各类书籍评论数的总数，后端传来的数据为类型type和总评论数comments
    public Result commentstatic(){
       Result result=new Result();
      return result.success(statisticService.commentstatic());
    }

    @GetMapping("/good")//各类书籍中好评数的总数,后端传来的数据为类型type和总评论数comments
    public Result goodcommentstatic(){
       Result result=new Result();
       return result.success(statisticService.goodcommentstatic());
    }

    @GetMapping("/weeklystastic")
    //近一周内每一天的登录人数（活跃人数），用折线图展示,logintime为日期，times为次数
    public Result statisticloginweek(){
       Result result=new Result();
       return result.success(statisticService.statisticloginweek());
    }


    @GetMapping("/most")
    //活跃度（登录次数）最多的前5个用户的用户名username和（活跃度）登录次数times
    public Result statisticmost(){

        Result result=new Result();
        return result.success(statisticService.statisticmost());
    }

    @GetMapping("/month")
    //统计近一年中每一个月的活跃度，也就是每一个月有多少人登录了，time为时间,times为次数
    public Result monthstatistic(){
       Result result=new Result();
       return result.success(statisticService.monthstatistic());
    }



}
