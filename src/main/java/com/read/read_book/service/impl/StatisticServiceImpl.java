package com.read.read_book.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.read.read_book.Mapper.CommentstaticMapper;
import com.read.read_book.Mapper.GoodcommentstaticMapper;
import com.read.read_book.Mapper.UserMapper;
import com.read.read_book.Mapper.UserstaticMapper;
import com.read.read_book.dto.*;
import com.read.read_book.pojo.User;
import com.read.read_book.pojo.Userstatistic;
import com.read.read_book.service.IUserService;
import com.read.read_book.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StatisticServiceImpl  implements StatisticService {
    @Autowired
    CommentstaticMapper commentstaticMapper;
    @Autowired
    GoodcommentstaticMapper goodcommentstaticMapper;
    @Autowired
    UserstaticMapper userstaticMapper;

    @Override
    public List<Commentstatic> commentstatic() {
        return commentstaticMapper.selectList(null);
    }

    @Override
    public List<Goodcommentstatic> goodcommentstatic() {
        return goodcommentstaticMapper.selectList(null);
    }

    @Override
    public List<loginstastic> statisticloginweek() {
        return userstaticMapper.statisticloginweek();
    }

    @Override
    public List<loginstatistic2> statisticmost() {
        return userstaticMapper.statisticmost();
    }

    @Override
    public List<loginstastic3> monthstatistic() {
        return userstaticMapper.monthstatistic();
    }
}
