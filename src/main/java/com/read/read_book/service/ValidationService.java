package com.read.read_book.service;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import com.read.read_book.pojo.Validation;

import java.util.Date;

public interface ValidationService extends IService<Validation> {
    Integer saveCode(String email, String code, Integer type, Date offsetMillisecond);
}
