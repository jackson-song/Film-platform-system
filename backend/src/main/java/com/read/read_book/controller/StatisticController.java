package com.read.read_book.controller;

import com.read.read_book.common.Result;
import com.read.read_book.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/statistic")
public class StatisticController {
    @Autowired
    CommentService commentService;
//    public Result commentstatic(){
//
//    }

}
