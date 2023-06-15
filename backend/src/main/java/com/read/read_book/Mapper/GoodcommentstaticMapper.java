package com.read.read_book.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.read.read_book.dto.Goodcommentstatic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodcommentstaticMapper extends BaseMapper<Goodcommentstatic> {


    @Select("SELECT * FROM `commentstatic` where type not in (select type from goodcommentstatic)")
    List<Goodcommentstatic> nogood();
}
