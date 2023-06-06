package com.read.read_book.controller;

import com.read.read_book.pojo.Book;
import com.read.read_book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    // ISBN 查找书籍
//    @RequestMapping("/searchbook/{ISBN}")
//    public List<Book> getBookByISBN(@PathVariable Long ISBN) {
//        return bookMapper.SerachBookByISBN(ISBN);
//    }

    @RequestMapping("/searchbookbybookname/{bookname}")
    public List<Book> getBookByBookname(@PathVariable String bookname) {
        return bookService.SearchBookByBookname(bookname);
    }
}
