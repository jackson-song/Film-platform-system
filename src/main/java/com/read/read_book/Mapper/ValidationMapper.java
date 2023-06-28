package com.read.read_book.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.read.read_book.pojo.Userstatistic;
import com.read.read_book.pojo.Validation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ValidationMapper extends BaseMapper<Validation> {
}
