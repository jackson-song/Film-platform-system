package com.read.read_book.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.read.read_book.dto.bookpage;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.BookBooktype;
import com.read.read_book.pojo.Booktype;
import com.read.read_book.service.BookService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
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

//    @PostMapping("/api/book/searchbookbytitle/")
//    public List<Book> getBookByTitle(@RequestParam Map<String, String> map) {
////        System.out.println("接收到map" + map);
////        String username = map.get("username");
////        String password = map.get("password");
////        String confirmedPassword = map.get("password_confirm");
//        String title = map.get("title");
//        System.out.println("收到的title:" + title);
//        return bookService.SearchBookByTitle(title);
//    }


//    @GetMapping("/admin")//按书名,isbn,作者查询,还没有进行分页
//    public List<Book> adminselbook(@RequestBody Book book){
//        System.out.println(book);
////        pagenum=(pagenum-1)* pagesize;
//        return bookService.adminselbook(book);
//    }

//    @GetMapping("/type")//按类型查询，没有进行分页
//    public List<BookBooktype> selectbookbytype(@RequestBody bookpage booktype){
//        System.out.println(booktype);
//        String name =booktype.getBooktypename();
//        return bookService.adminselbooktype(name);
//    }


//    @GetMapping ("/type")//按类型查询，没有进行分页
//    public List<BookBooktype> selectbookbytype(@RequestBody Booktype booktype){
//        System.out.println(booktype);
//        String name =booktype.getBooktypename();
//        return bookService.adminselbooktype(name);
//    }

//    @GetMapping("/pageByauthor")//分页按作者查询，前端需要传的数据为pagenum,pagesize,作者
//    public Page<Book> pageByConditions(@RequestBody bookpage bookpage) {
//        System.out.println(bookpage);
//        return bookService.bookpagebyauthor(bookpage);
//    }//下面有一个方法可以全部进行模糊查询，此方法可不要
//    @GetMapping("/pageByisbn")//分页按isbn查询，前端需要传的数据为pagenum,pagesize,isbn
//    public Page<Book> pageByisbn(@RequestBody bookpage bookpage) {
//        System.out.println(bookpage);
//        return bookService.bookpagebyisbn(bookpage);
//    }//下面有一个方法可以全部进行模糊查询，此方法可不要
//    @GetMapping("/pageByBookall")//分页按bookname查询，前端需要传的数据为pagenum,pagesize,书名
//    public Page<Book> pageByBookname(@RequestBody bookpage bookpage)
//    //这个方法是传的body，下面有一个完善更好的用法也就是路径（正规一点）
//    {
//        System.out.println(bookpage);
//        return bookService.bookpagebyBookall(bookpage);
//    }//根据书名，作者，isbn进行模糊查询,用这一个方法可以对这三个信息都进行模糊查询，前端需要传pagenum,pagesize,书名，isbn,作者


    @GetMapping("/pageByBookalltest")//查找书籍用这个
    //对进行查询的书据进行分页，使用路径的方式（与豆瓣官网一致，推荐使用此方法进行查找分页）
    // 需要传入的数据为页号page，页的大小size,搜索的关键字text
    public Page<Book> pageByBookall( @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam Object text) {
        System.out.println(page);
//        Object text1=text;
        System.out.println(size);
        System.out.println(text);
        return bookService.bookpagebyall(page,size,text);
    }
    @PutMapping()//
    public Map<String, Object> Udbook(@RequestBody Book book)
    //前端的请求方法为put,前端传入修改的信息,isbn不能少,因为要根据isbn来进行修改,isbn不能修改
    {
        Map<String, Object> map = new HashMap<>();
        System.out.println(book);
        int i= bookService.Udbook(book);
        if(i!=0){
            map.put("message","success");
            return map;
        }else{
            map.put("error","failed");
            return map;
        }
    }
   @PostMapping
    public Map<String, Object> addbook(@RequestBody Book book)
   //添加书籍信息,前端传入书籍的所有信息
   {
        Map<String, Object> map = new HashMap<>();
        System.out.println(book);
        int i=bookService.addbook(book);
        if(i==1){
            map.put("message","success");
            return map;
        }else{
            map.put("error","failed");
            return map;
        }
    }

    @DeleteMapping
    public Map<String, Object> delbook(@RequestBody Book book)
    //前端的请求方式为delete,删除书籍信息,前端传入需要删除的书籍的isbn
    {
        Map<String, Object> map = new HashMap<>();
        System.out.println(book);
        long n=book.getIsbn();
        int i=bookService.delbook(n);
        if(i==1){
            map.put("message","success");
            return map;
        }else{
            map.put("error","failed");
            return map;
        }
    }
    @GetMapping("/isbn")
    //前端需要传查询书籍的isbn
    public Book detail(@RequestBody Book book){
        long n=book.getIsbn();
        return bookService.detailbook(n);
    }//查询书籍详情
//    @GetMapping ("/test")//按类型查询，没有进行分页
//    public int selectbooknewest(@RequestBody Book book){
//        System.out.println(book);
//        System.out.println(book.getPublicationtime());
//        return 1;
//    }
    @GetMapping("/newest")
    //不用传数据
    public List<Book> selectnewest(){
        return bookService.newestbook();
    }

    @GetMapping("/hotbook")
    //不用传数据
    public List<Book> selecthot(){
        return bookService.hotbook();
    }
}
