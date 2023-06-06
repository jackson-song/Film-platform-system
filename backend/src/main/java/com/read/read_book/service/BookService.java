package com.read.read_book.service;

import com.read.read_book.pojo.Book;

import java.util.List;

public interface BookService {

    //书名模糊查询
    //select * from book where bookname like '%bookname%';
    public List<Book> SearchBookByBookname(String bookname);
}
