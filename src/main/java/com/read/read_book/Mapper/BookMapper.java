package com.read.read_book.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.Booktype;
import com.read.read_book.pojo.type;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book> {

    @Select("select * from book where isbn = #{ISBN}")
    List<Book> SerachBookByISBN(@Param("ISBN") Long ISBN);

//    List<Book> SearchBookByBookname(String bookname);
    List<Book> adminselbook(@Param("bookname") Object text,@Param("isbn") Object text1,@Param("author") Object text2,  int page, int size);//管理员查找书籍
    Integer total(@Param("bookname") Object text,@Param("isbn") Object text1,@Param("author") Object text2);
    @Select("select booktypeid from booktype where booktypename = #{booktypename}")
    int selectbytypename(@Param("booktypename") String booktypename);//根据typename得到typeid

    @Select("SELECT * FROM `booktype` where type = #{type}  limit #{page}, #{size}")
    @Results({
            @Result(column = "isbn",property = "isbn"),
            @Result(column = "type",property = "type"),
            @Result(column = "isbn",property = "books",javaType = Book.class,
                    one=@One(select = "com.read.read_book.Mapper.BookMapper.selectbyisbn")
            )
    })//根据typeid查找书籍，分类查找,测试
    List<type> selectbookbytypetest(@Param("type") String type, Integer page, Integer size);




    @Select("SELECT isbn FROM `book_booktype` where booktypeid = #{booktypeid}")
    int seletypeid(@Param("booktypeid") int booktypeid);//根据typeid查找isbn
    @Select("select *from book where isbn= #{isbn},limit #{page}, #{size}")
    List<Book> selectbyISBN(@Param("isbn") long isbn,int page,int size);//根据isbn查找书籍


    Integer Udbook(Book book);//管理员修改书籍信息

    @Delete("delete from book where isbn = #{isbn} ")
    Integer delbook(@Param("isbn") long isbn);//管理员删除书籍信息


    @Select("select *from book where isbn= #{isbn}")
    Book selectbyisbn(@Param("isbn") long isbn);

    @Select("select *from (select *from book order by ratnum desc limit 50)a limit #{page},#{size}")
    List<Book> selectpagebyhot(Integer page,Integer size);

    @Select("select *from (select *from book ORDER BY STR_TO_DATE(Publicationtime, '%Y-%m-%d') DESC limit 50)a limit #{page},#{size}")
    List<Book> selectpagebynewest(Integer page,Integer size);




}
