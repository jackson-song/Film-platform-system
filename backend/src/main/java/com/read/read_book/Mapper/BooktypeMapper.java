package com.read.read_book.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.Booktype;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BooktypeMapper extends BaseMapper<Booktype> {

    Integer Udbook(Booktype book);

    @Delete("delete from type where isbn = #{isbn} ")
    Integer delbook(@Param("isbn") long isbn);//管理员删除书籍信息
}
