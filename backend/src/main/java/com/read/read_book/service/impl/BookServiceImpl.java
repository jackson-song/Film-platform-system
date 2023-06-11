package com.read.read_book.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.read.read_book.Mapper.BookMapper;
import com.read.read_book.dto.bookpage;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.BookBooktype;
import com.read.read_book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;

import static java.util.stream.DoubleStream.concat;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;

//    @Override
//    public List<Book> SearchBookByTitle(String title) {
//        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("Bookname",title);
//        List<Book> books = bookMapper.selectList(queryWrapper);
//        return books;
//    }

    @Override
    public List<Book> newestbook() {
        List<Book> books = bookMapper.selectList(Wrappers.<Book>query().orderByDesc("Publicationtime").last("limit 50"));
        return books;
    }//最新书籍

    @Override
    public Book detailbook(Long isbn) {
        return bookMapper.selectbyisbn(isbn);
    }

    @Override
    public Integer Udbook(Book book)//修改书籍信息
    {
        return bookMapper.Udbook(book);
    }

    @Override
    public Integer addbook(Book book) {
        int n=bookMapper.insert(book);
        return n;
    }

    @Override
    public Integer delbook(Long isbn) {
       int rs= bookMapper.delbook(isbn);
       return rs;
    }//删除书籍信息

//    @Override
//    public List<Book> adminselbook(Book book) //按作者，书名，isbn查询书籍
//    {
//        return bookMapper.adminselbook(book);
//    }//后面有完善版
//
//    @Override
//    public Page<Book> bookpagebyauthor(bookpage bookpage) {
//        Page<Book> page = new Page(bookpage.getPagenum(), bookpage.getPagesize());
//        QueryWrapper<Book> wrapper = new QueryWrapper<>();
//        wrapper.eq("author", bookpage.getAuthor());
////        wrapper.like("name", t.getName());
//          return bookMapper.selectPage(page,wrapper);
////        return this.(page, wrapper);
//    }//这个暂时不用，用后面整体最终版本
//
//    @Override
//    public Page<Book> bookpagebyisbn(bookpage bookpage) {
//        Page<Book> page = new Page(bookpage.getPagenum(), bookpage.getPagesize());
//        QueryWrapper<Book> wrapper = new QueryWrapper<>();
//        wrapper.eq("isbn", bookpage.getISBN());
////        wrapper.like("name", t.getName());
//        return bookMapper.selectPage(page,wrapper);
////        return this.(page, wrapper);
//    }//用最后最终版本
//
//    @Override
//    public Page<Book> bookpagebyBookall(bookpage bookpage) {
//        Page<Book> page = new Page(bookpage.getPagenum(), bookpage.getPagesize());
//        QueryWrapper<Book> wrapper = new QueryWrapper<>();
//        wrapper.like("Bookname", bookpage.getBookname()).or().like("author", bookpage.getAuthor()).or().like("isbn",bookpage.getISBN());
////        wrapper.like("Bookname", bookpage.getBookname());
////        wrapper.like("Bookname", bookpage.getBookname());
////        wrapper.like("name", t.getName());
////        String bname=bookpage.getBookname();
////        System.out.println(bname);
////        wrapper.like("Bookname",bname);
////        System.out.println(wrapper);
//        return bookMapper.selectPage(page,wrapper);
//    }//根据书名，作者，isbn进行模糊查询，后面有完善版

    @Override
    public List<BookBooktype> adminselbooktype(String booktypename) {
        int m=bookMapper.selectbytypename(booktypename);
        System.out.println(m);
        return bookMapper.selectbookbytype(m);
    }//按类型查询

    @Override
    public Page<Book> bookpagebyall(int page, int size, Object text) {
        int pagenum=(page-1)*size;
        Page<Book> page1=new Page<>(pagenum,size);
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        wrapper.like("Bookname",text).or().like("author", text).or().like("isbn",text);
        return bookMapper.selectPage(page1,wrapper);
    }//按书籍，作者，isbn模糊查询分页最终版

    @Override
    public Page<Book> admintype(bookpage bookpage) {
            int m=bookMapper.selectbytypename(bookpage.getBooktypename());
            System.out.println(m);
            int isbn=bookMapper.seletypeid(m);
            System.out.println(isbn);
            Page<Book> page = new Page(bookpage.getPagenum(), bookpage.getPagesize());
            QueryWrapper<Book> wrapper = new QueryWrapper<>();
            wrapper.eq(" booktypename;", bookpage.getBooktypename());
//        wrapper.like("name", t.getName());
            return bookMapper.selectPage(page,wrapper);
//        return bookMapper.selectbookbytype(m);
        }
    }



