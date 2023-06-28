package com.read.read_book.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.read.read_book.Mapper.BookMapper;
import com.read.read_book.Mapper.BookshelfMapper;
import com.read.read_book.Mapper.BooktypeMapper;
//import com.read.read_book.Mapper.TypeMapper;
import com.read.read_book.Mapper.TypeMapper;
import com.read.read_book.common.Result;
import com.read.read_book.dto.Bookshelf;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.Booktype;
import com.read.read_book.pojo.type;
import com.read.read_book.service.BookService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;
    @Autowired
    BooktypeMapper booktypeMapper;

    @Autowired
    BookshelfMapper bookshelfMapper;

    @Autowired
    TypeMapper typeMapper;

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
        Result result=new Result();
        page=(page-1)*size;
        return result.success(bookMapper.selectpagebyhot(page,size),50);
    }//热门书籍

    @Override
    public Result newestbook(int page,int size) {
        Result result=new Result();
        page=(page-1)*size;
        return result.success(bookMapper.selectpagebynewest(page,size),50);
    }//最新书籍

    @Override
    public Book detailbook(Long isbn) {

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
        return bookMapper.Udbook(book);
        }else return 0;
    }

    @Override
    public Integer addbook(Booktype book) {
        Book book1=new Book();
        System.out.println(book);
        BeanUtils.copyProperties(book, book1);
        System.out.println(book1);
        int m=booktypeMapper.insert(book);
        int n=bookMapper.insert(book1);
        String type=book.getType();
        Long isbn=book.getIsbn();
        type type1=new type();
        type1.setIsbn(isbn);
        type1.setType(type);
        typeMapper.insert(type1);
        if(m!=0&&n!=0){
            return bookMapper.Udbook(book1);}else return 0;
    }

    @Override
    public Integer delbook(Long isbn) {
        int r= typeMapper.delbook(isbn);
       int rs= bookMapper.delbook(isbn);
       int rs1=booktypeMapper.delbook(isbn);
       return rs;
    }//删除书籍信息


    @Override
    public Result bookpagebyall(int page, int size, Object text) {
        Result result=new Result();
        int pagenum=(page-1)*size;
//        Page<Book> page1=new Page<>(page,size);
//        QueryWrapper<Book> wrapper = new QueryWrapper<>();
//        wrapper.like("Bookname",text).or().like("author", text).or().like("isbn",text);
        Object text1=text;
        int total =bookMapper.total(text,text1,text);
//        return bookMapper.selectPage(page1,wrapper);
        return result.success(bookMapper.adminselbook(text,text1,text,pagenum,size),total);
    }//按书籍，作者，isbn模糊查询分页最终版

    @Override
    public Page<Booktype> pagebytype(int page,int size,String type) {
            Page<Booktype> page1 = new Page(page, size);
            QueryWrapper<Booktype> wrapper = new QueryWrapper<>();
            wrapper.like("type",type );
            return booktypeMapper.selectPage(page1,wrapper);
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



    @Override
    public Result addshelf(Integer userid, Long isbn)
    //添加这一本书籍到我的书架
    {
        Result result=new Result();
        Bookshelf bookshelf1=new Bookshelf();
        QueryWrapper<Bookshelf> wrapper = new QueryWrapper<>();
        wrapper.eq("userid",userid);
        wrapper.eq("isbn",isbn);
        bookshelfMapper.selectOne(wrapper);
//        System.out.println(list.get(0));
        if(bookshelfMapper.selectOne(wrapper)==null){
        Bookshelf bookshelf=new Bookshelf();
        bookshelf.setIsbn(isbn);
        bookshelf.setUserid(userid);
      if(bookshelfMapper.insert(bookshelf)!=0){
            return  result.success();
    }else {
          return result.error("400","faild");
      }}else return result.error("500","这本书已经在您的书架中了，请注意查看书架");
}


    @Override
    public Result selshelf(Integer userid)
    //查看我的书架
    {
        Result result=new Result();
        if(bookshelfMapper.selbookshelf(userid)!=null){
            Book booktest=new Book();
            List<Book> book=new ArrayList<>();
            List<Bookshelf> a=bookshelfMapper.selbookshelf(userid);
            for(Bookshelf bookshelf: a){
                System.out.println(bookshelf.getBooks());
                book.add(bookshelf.getBooks());
            }
            return result.success(book);//书架中的书籍信息
        }else {
            return result.error("400","faild");
        }
    }
    @Override
    public  Result delshelf(Integer userid, Long isbn)//从书架中移除书籍
    {
        Result result=new Result();
        QueryWrapper<Bookshelf> wrapper = new QueryWrapper<>();
        wrapper.eq("userid",userid);
        wrapper.eq("isbn",isbn);
//        bookshelfMapper.delete(wrapper);
        if(bookshelfMapper.delete(wrapper)!=0){
            return result.success();
        }else return result.error("400","faild");
    }

    @Override
    public Result Booktypetest(String type ,Integer page,Integer size)
            //进行按类型进行查询，连表进行分页查询
    {
        Result result=new Result();
        page=(page-1)*size;
        System.out.println(bookMapper.selectbookbytypetest(type, page, size));
        if(bookMapper.selectbookbytypetest(type, page, size)!= null){
            Book booktest=new Book();
            List<Book> book=new ArrayList<>();
            List<type> a=bookMapper.selectbookbytypetest(type, page, size);
            for(type type1: a){
                System.out.println(type1.getBooks());
                book.add(type1.getBooks());
            }
            return result.success(book);//这种类型的书籍
        }else {
            return result.error("400","faild");
        }
    }
}



