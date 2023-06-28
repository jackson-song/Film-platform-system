package com.read.read_book.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.read.read_book.pojo.Comment;
import com.read.read_book.pojo.Commentunionbook;
import org.apache.ibatis.annotations.*;

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
    @Select("select *from (select *from commentandbook order by commenttime desc limit 50)a limit #{page},#{size}")
    List<Commentunionbook> commentnewest(Integer page, Integer size);//全部评论的最新

    @Select("select *from (select *from commentandbook order by likeing desc limit 50)a limit #{page},#{size}")
    List<Commentunionbook> commenthot(Integer page,Integer size);//全部评论的热门


    @Select("select *from (select *from comment where isbn=#{isbn} order by commenttime desc limit 50)a limit #{page},#{size}")
    List<Comment> commentbooknewest(@Param("isbn") Long isbn,Integer page,Integer size);//全部评论的最新
    @Select("select *from (select *from comment where isbn=#{isbn} order by likeing desc limit 50)a limit #{page},#{size}")
    List<Comment> commentbookhot(@Param("isbn") Long isbn,Integer page,Integer size);//全部评论的最热
    @Select("select count(*) from comment ")
    Integer total();
    @Select("select count(*) from comment where isbn =#{isbn}")
    Integer totalbook(Long isbn);


    @Update("update comment set likeing =#{likeing} where commentid =#{commentid}")
    Integer uplikeing(Long likeing,Integer commentid);
}

