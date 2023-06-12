package com.read.read_book.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.BookBooktype;
import com.read.read_book.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book> {

    @Select("select * from book where isbn = #{ISBN}")
    List<Book> SerachBookByISBN(@Param("ISBN") Long ISBN);

//    List<Book> SearchBookByBookname(String bookname);
    List<Book> adminselbook(Book book);//管理员查找书籍
    Integer total(Book book);
    @Select("select booktypeid from booktype where booktypename = #{booktypename}")
    int selectbytypename(@Param("booktypename") String booktypename);//根据typename得到typeid

    @Select("SELECT * FROM `book_booktype` where booktypeid = #{booktypeid}")
    @Results({
            @Result(column = "isbn",property = "isbn"),
            @Result(column = "booktypeid",property = "booktypeid"),
            @Result(column = "isbn",property = "book",javaType = Book.class,
                    one=@One(select = "com.read.read_book.Mapper.BookMapper.selectbyISBN")
            )
    })//根据typeid查找书籍，分类查找
    List<BookBooktype> selectbookbytype(@Param("booktypeid") int booktypeid);
    @Select("SELECT isbn FROM `book_booktype` where booktypeid = #{booktypeid}")
    int seletypeid(@Param("booktypeid") int booktypeid);//根据typeid查找isbn
    @Select("select *from book where isbn= #{isbn}")
    List<Book> selectbyISBN(@Param("isbn") long isbn);//根据isbn查找书籍


    Integer Udbook(Book book);//管理员修改书籍信息

    @Delete("delete from book where isbn = #{isbn} ")
    Integer delbook(@Param("isbn") long isbn);//管理员删除书籍信息


    @Select("select *from book where isbn= #{isbn}")
    Book selectbyisbn(@Param("isbn") long isbn);


}
