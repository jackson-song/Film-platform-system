package com.read.read_book.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.read.read_book.dto.Bookshelf;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.BookBooktype;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookshelfMapper extends BaseMapper<Bookshelf> {
    @Select("SELECT * FROM `bookshelf` where userid = #{userid}")
    @Results({
            @Result(column = "isbn",property = "isbn"),
            @Result(column = "userid",property = "userid"),
            @Result(column = "isbn",property = "books",javaType = List.class,
                    one=@One(select = "com.read.read_book.Mapper.BookMapper.SerachBookByISBN")
            )
    })//根据typeid查找书籍，分类查找
    List<Bookshelf> selbookshelf(@Param("userid") int userid);
}
