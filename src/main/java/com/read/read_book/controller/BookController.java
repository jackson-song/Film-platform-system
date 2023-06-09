package com.read.read_book.controller;
import com.read.read_book.common.Result;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.Booktype;
import com.read.read_book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public Result pageByBookall(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam Object text) {
        System.out.println(page);
//        Object text1=text;
        System.out.println(size);
        System.out.println(text);
        Result result=new Result();
        System.out.println(bookService.bookpagebyall(page,size,text));
        return bookService.bookpagebyall(page,size,text);
//        return bookService.bookpagebyall(page,size,text);
    }
    @GetMapping("/pagebytype")
    //根据类别查询，对进行查询的书据进行分页，使用路径的方式（与豆瓣官网一致，推荐使用此方法进行查找分页）
    // 需要传入的数据为页号page，页的大小size,类别type
    public Result pagebytype(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam String type){
        Result result=new Result();
        return result.success(bookService.pagebytype(page,size,type));
    }


    @GetMapping("/pagebytypedetail")
    //根据类别详细查询，对进行查询的书据进行分页，使用路径的方式（与豆瓣官网一致，推荐使用此方法进行查找分页）
    // 在类型中的搜索框中搜索这一类型相对应搜索内容的书籍，前端必须传的数据为类型，页面，页面大小，搜索内容
    public Result sepagebytype(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam String type,
                               @RequestParam Object text){
        Result result=new Result();
        return result.success(bookService.sepagebytype(page, size, type, text));
    }
    @PutMapping()//
    public Result Udbook(@RequestBody Book book)
    //修改书籍信息，前端的请求方法为put,前端传入修改的信息,isbn不能少,因为要根据isbn来进行修改,isbn不能修改
    {
        Map<String, Object> map = new HashMap<>();
        System.out.println(book);
        int i= bookService.Udbook(book);
        Result result=new Result();
        if(i!=0){
//            map.put("message","success");
            return result.success();
        }else{
//            map.put("error","failed");
//            return map;
            return result.error("400","failed");
        }
    }
   @PostMapping
    public Result addbook(@RequestBody Booktype book)
   //添加书籍信息,前端传入书籍的所有信息,传入的书籍信息要包括类别type
   {
        Map<String, Object> map = new HashMap<>();
        System.out.println(book);
        int i=bookService.addbook(book);
        Result result=new Result();
        if(i!=0){
//            map.put("message","success");
            return result.success();
        }else{
//            map.put("error","failed");
            return result.error("400","failed");
        }
    }

//    @DeleteMapping
//    public Result delbook(@RequestBody Book book)
//    //前端的请求方式为delete,删除书籍信息,前端传入需要删除的书籍的isbn
//    {
//        Map<String, Object> map = new HashMap<>();
//        System.out.println(book);
//        long n=book.getIsbn();
//        int i=bookService.delbook(n);
//        Result result=new Result();
//        if(i==1){
////            map.put("message","success");
//            return result.success();
//        }else{
////            map.put("error","failed");
//            return result.success();
//        }
//    }

    //    @DeleteMapping
//    public Result delbook(@RequestBody Book book)
//    //前端的请求方式为delete,删除书籍信息,前端传入需要删除的书籍的isbn
//    {
//        Map<String, Object> map = new HashMap<>();
//        System.out.println(book);
//        long n=book.getIsbn();
//        int i=bookService.delbook(n);
//        Result result=new Result();
//        if(i==1){
////            map.put("message","success");
//            return result.success();
//        }else{
////            map.put("error","failed");
//            return result.success();
//        }
//    }
    @DeleteMapping("/{isbn}")
    //前端的请求方式为delete,删除书籍信息,前端传入需要删除的书籍的isbn,
    //路径为http://localhost:3000/books/1，1是isbn,传哪个isbn
    public Result delbook(@PathVariable Long isbn){
        int i=bookService.delbook(isbn);
        Result result=new Result();
        if(i!=0){
            return result.success();
        }else{
            return result.error("400","failed");
        }
    }
    //书籍详情，前端需要传查询书籍的isbn,
    // 前端路径为http://localhost:3000/books/9787532183562，9787532183562为isbn
    @GetMapping("/{isbn}")
    public Result detail(@PathVariable Long isbn){
        Result result=new Result();
        return result.success(bookService.detailbook(isbn));
    }//查询书籍详情
//    @GetMapping ("/test")//按类型查询，没有进行分页
//    public int selectbooknewest(@RequestBody Book book){
//        System.out.println(book);
//        System.out.println(book.getPublicationtime());
//        return 1;
//    }
    @GetMapping("/newest")
    //需要传入页数，页的大小，向前端传回前50最新书籍
    public Result selectnewest(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size){
        Result result=new Result();
        return bookService.newestbook(page,size);
//        return bookService.newestbook();
    }

    @GetMapping("/hotbook")
    //传入页号和页的大小，返回前50本热门书籍
    public Result selecthot(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size){
        Result result=new Result();
        return bookService.hotbook(page,size);
    }


    @PostMapping("/shelf/{userid}/{isbn}")
    //将书籍添加到书架，加入书架功能， //如果此用户对这一本书籍已经有书架了，则后端会给前端报500，并msg为这本书已经在您的书架中了，请注意查看书架
    public  Result addshelf(@PathVariable Integer userid,@PathVariable Long isbn){
         return bookService.addshelf(userid, isbn);

    }


    @GetMapping("/shelf/{userid}")
    //查询自己书架中的书籍，返回的data中的books为书籍的信息，这个放在个人信息页面中
    public Result selshelf(@PathVariable Integer userid){
        System.out.println(userid);
//        System.out.println(bookService.selshelf(userid));
        return bookService.selshelf(userid);
    }



    @DeleteMapping("/shelf/{userid}/{isbn}")//删除书架中的书籍
    public Result delshelf(@PathVariable Integer userid,@PathVariable Long isbn)
    {
        System.out.println(userid);
        return bookService.delshelf(userid, isbn);
    }

    @GetMapping("/typetest/{type}")//根据类别进行连表进行分页查询，优化了数据库表
    public Result Booktypetest(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size,@PathVariable String type)
    {
        Result result=new Result();
        System.out.println(type);
        return bookService.Booktypetest(type,page,size);
    }
//
}
