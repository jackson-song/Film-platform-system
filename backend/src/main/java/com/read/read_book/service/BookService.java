package com.read.read_book.service;

import com.read.read_book.pojo.Book;

import java.util.List;

public interface BookService {

    //书名模糊查询
    //select * from book where bookname like '%bookname%';
    public List<Book> SearchBookByTitle(String title);

    //书籍分类查询 (?连表查询)


    //热门书籍


    //最新书籍


    //个性推荐书籍 (?推荐)


    //书籍详情 select * from book where isbn = isbn,也是后台查询书籍功能


    //修改书籍信息,后台功能

}
