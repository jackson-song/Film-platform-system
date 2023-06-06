package com.read.read_book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.read.read_book.mapper.BookMapper;
import com.read.read_book.pojo.Book;
import com.read.read_book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;

    @Override
    public List<Book> SearchBookByBookname(String bookname) {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("Bookname",bookname);
        List<Book> books = bookMapper.selectList(queryWrapper);
        return books;
    }
}
