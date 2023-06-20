package com.read.read_book.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.read.read_book.pojo.Likeings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LikeingsMapper extends BaseMapper<Likeings> {
    @Select("select * from likeings where userid = #{userid} and commentid= #{commentid}")
    Likeings getlike(Integer userid, Integer commentid);


}
