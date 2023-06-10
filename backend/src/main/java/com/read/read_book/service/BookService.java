package com.read.read_book.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.read.read_book.dto.bookpage;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.BookBooktype;

import java.io.Serializable;
import java.util.List;

public interface BookService {

    //书名模糊查询
    //select * from book where bookname like '%bookname%';
    public List<Book> SearchBookByTitle(String title);
    //后台有一个功能是可以按书名，isbn,作者进行模糊查询分页可以使用

    //书籍分类查询 (?连表查询),后台已经做完这一部分功能
//    public List<BookBooktype> adminselbooktype(String booktypename);//管理员根据类型查询

    //热门书籍


    //最新书籍,只取前50本
   List<Book> newestbook();

    //个性推荐书籍 (?推荐)


    //书籍详情 select * from book where isbn = isbn,也是后台查询书籍功能
     Book detailbook(Long isbn);

    //修改书籍信息,后台功能
    Integer Udbook(Book book);

    //增加书籍信息 后台
    Integer addbook(Book book);

    //删除书籍信息
   Integer delbook(Long isbn);


//    public List<Book> adminselbook(Book book);//管理员查询书籍（isbn,书名，作者）
//
//
//    public Page<Book> bookpagebyauthor(bookpage bookpage);//按作者查询，分页
//
//    public Page<Book> bookpagebyisbn(bookpage bookpage);//按isbn查询，分页

//    public Page<Book> bookpagebyBookall(bookpage bookpage);//按三者结合查询，分页
    public List<BookBooktype> adminselbooktype(String booktypename);//管理员根据类型查询

    public Page<Book> bookpagebyall(int page,int size,Object text);//分页最终版，按三者查询分页



//    public Page<Book> admintype(bookpage bookpage);//管理员查询分类分页
}
