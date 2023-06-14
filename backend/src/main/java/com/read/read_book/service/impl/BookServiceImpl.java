package com.read.read_book.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.read.read_book.Mapper.BookMapper;
import com.read.read_book.Mapper.BooktypeMapper;
//import com.read.read_book.Mapper.TypeMapper;
import com.read.read_book.common.Result;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.Booktype;
import com.read.read_book.service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;
    @Autowired
    BooktypeMapper booktypeMapper;

//    @Autowired
//    BookBooktypeMapper bookBooktypeMapper;

//    @Autowired
//    TypeMapper typeMapper;

//    @Override
//    public List<Book> SearchBookByTitle(String title) {
//        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("Bookname",title);
//        List<Book> books = bookMapper.selectList(queryWrapper);
//        return books;
//    }

    @Override
    public Result hotbook(int page, int size) {
//        Page<Book> page1=new Page<>(page,size);
//        QueryWrapper<Book> wrapper = new QueryWrapper<>();
//        wrapper.orderByDesc("ratnum");
////        Wrappers.<Book>query().orderByDesc("Publicationtime").last("limit 50")
////        wrapper.like("Bookname",text).or().like("author", text).or().like("isbn",text);
////        List<Book> books = bookMapper.selectList(Wrappers.<Book>query().orderByDesc("Publicationtime").last("limit 50"));
////       return page1.setRecords(bookMapper.selectList(wrapper).subList(0, 20));
//        wrapper.last("LIMIT " + (10 - 1) + "," + 10); // 修改limit参数为offset参数 // 使用last方法添加自定义的SQL语句，限制输出结果的数量
//        return bookMapper.selectPage(page1,wrapper);
////        List<Book> books = bookMapper.selectList(Wrappers.<Book>query().orderByDesc("ratnum").last("limit 50"));
////        return books;
        Result result=new Result();
        page=(page-1)*size;
        return result.success(bookMapper.selectpagebyhot(page,size),50);
    }//热门书籍

    @Override
    public Result newestbook(int page,int size) {
//        Page<Book> page1=new Page<>(page,size);
//        QueryWrapper<Book> wrapper = new QueryWrapper<>();
//        wrapper.orderByDesc("Publicationtime");
//        Wrappers.<Book>query().orderByDesc("Publicationtime").last("limit 50")
//        wrapper.like("Bookname",text).or().like("author", text).or().like("isbn",text);
//        List<Book> books = bookMapper.selectList(Wrappers.<Book>query().orderByDesc("Publicationtime").last("limit 50"));
//        return bookMapper.selectPage(page1,wrapper);
        Result result=new Result();
        page=(page-1)*size;
        return result.success(bookMapper.selectpagebynewest(page,size),50);
    }//最新书籍

    @Override
    public List<Book> detailbook(Long isbn) {

        return bookMapper.selectbyisbn(isbn);
    }

    @Override
    public Integer Udbook(Book book)//修改书籍信息
    {
        int m=bookMapper.Udbook(book);
        Booktype booktype=new Booktype();
        BeanUtils.copyProperties(book, booktype);
        System.out.println(booktype);
        int n=booktypeMapper.Udbook(booktype);
        if(m!=0&&n!=0){
        return bookMapper.Udbook(book);}else return 0;
    }

    @Override
    public Integer addbook(Booktype book) {
        Book book1=new Book();
        BeanUtils.copyProperties(book, book1);
        int m=booktypeMapper.insert(book);
        int n=bookMapper.insert(book1);
        if(m!=0&&n!=0){
            return bookMapper.Udbook(book1);}else return 0;
    }

    @Override
    public Integer delbook(Long isbn) {
       int rs= bookMapper.delbook(isbn);
       int rs1=booktypeMapper.delbook(isbn);
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

//    @Override
//    public List<BookBooktype> adminselbooktype(String booktypename) {
//        int m=bookMapper.selectbytypename(booktypename);
//        System.out.println(m);
//        return bookMapper.selectbookbytype(m);
//    }//按类型查询

    @Override
    public Page<Book> bookpagebyall(int page, int size, Object text) {
        int pagenum=(page-1)*size;
        Page<Book> page1=new Page<>(page,size);
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        wrapper.like("Bookname",text).or().like("author", text).or().like("isbn",text);
        return bookMapper.selectPage(page1,wrapper);
    }//按书籍，作者，isbn模糊查询分页最终版

    @Override
    public Page<Booktype> pagebytype(int page,int size,String type) {
            Page<Booktype> page1 = new Page(page, size);
            QueryWrapper<Booktype> wrapper = new QueryWrapper<>();
            wrapper.like("type",type );
//        wrapper.like("name", t.getName());
            return booktypeMapper.selectPage(page1,wrapper);
//        return bookMapper.selectbookbytype(m);
        }

    @Override
    public Page<Booktype> sepagebytype(int page, int size, String type, Object text)
    //在类型中的搜索框中搜索这一类型相对应搜索内容的书籍，前端必须传的数据为类型，页面，页面大小，搜索内容
    {
        Page<Booktype> page1 = new Page(page, size);
        QueryWrapper<Booktype> wrapper = new QueryWrapper<>();
        wrapper.eq("type",type ).and(w -> w.like("Bookname",text).or().like("author", text).or().like("isbn",text));
        return booktypeMapper.selectPage(page1,wrapper);
    }

//    @Override
//    public Page<BookBooktype> pagetest(int page, int size, String type) {
//
//        Page<Booktype> page1 = new Page(page, size);
//        QueryWrapper<Booktype> wrapper = new QueryWrapper<>();
//        wrapper.like("type",type );
////        wrapper.like("name", t.getName());
//        return booktypeMapper.selectPage(page1,wrapper);
//    }


}



