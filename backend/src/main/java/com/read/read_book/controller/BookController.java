package com.read.read_book.controller;

import com.read.read_book.pojo.Book;
import com.read.read_book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    // ISBN 查找书籍
//    @RequestMapping("/searchbook/{ISBN}")
//    public List<Book> getBookByISBN(@PathVariable Long ISBN) {
//        return bookMapper.SerachBookByISBN(ISBN);
//    }

//    @RequestMapping("/searchbookbybooktitle/{title}")
//    public List<Book> getBookByTitle(@PathVariable String title) {
//        return bookService.SearchBookByTitle(title);
//    }

    @PostMapping("/api/book/searchbookbytitle/")
    public List<Book> getBookByTitle(@RequestParam Map<String, String> map) {
//        System.out.println("接收到map" + map);
//        String username = map.get("username");
//        String password = map.get("password");
//        String confirmedPassword = map.get("password_confirm");
        String title = map.get("title");
        System.out.println("收到的title:" + title);
        return bookService.SearchBookByTitle(title);
    }
}
