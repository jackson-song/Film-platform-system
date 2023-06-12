package com.read.read_book.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.read.read_book.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {


    @Select("select * from comment where userid = #{userid}")
    List<Comment> seluserment(@Param("userid") int userid);

    Integer updatecomment(Comment comment);

    @Update("update comment set likeing =#{likeing} where commentid =#{commentid}")
    Integer addlike(@Param("likeing") Long likeing,@Param("commentid") Integer commentid);

    @Select("select *from comment where commentid = #{commentid}")
    Comment secomment(@Param("commentid") Integer commentid);

//    @Select("select *from comment where userid= #{userid} limit #{page},#{size}" )
//    List selectpage(int userid,int page,int size);
}

