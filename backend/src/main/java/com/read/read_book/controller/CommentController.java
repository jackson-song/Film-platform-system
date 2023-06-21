package com.read.read_book.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.read.read_book.common.Result;
import com.read.read_book.dto.logindto;
import com.read.read_book.pojo.Comment;
import com.read.read_book.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController {
//
    @Autowired
    CommentService commentService;
//
    // 书的所有评论,已经实现分页，前端主要传的数据为页数Page和页的大小size,还有isbn
    @GetMapping("/BookComment")
    public Result getBookByBookname(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam Long isbn) {
        Result result=new Result();
        return result.success(commentService.getCommentByISBN(page, size, isbn));
    }


    @GetMapping("/usercomment/{userid}/{isbn}")
    //根据用户和书名查询用户的评论，用户对应这一本书的评论，前端需要传的数据为userid和isbn
    //通过路径传参数，http://localhost:3000/comments/usercomment/1/9787506365437，这就是userid为1，isbn为9787506365437的书评
    public  Result getcommentbyidandisbn(@PathVariable  Integer userid,@PathVariable Long isbn){
        Result result=new Result();
       return result.success(commentService.commentbyuseridandisbn(userid, isbn)) ;
    }
    // 用户的所有评论，已经实现分页，前端需要上传的数据为页号，
    @GetMapping("/allComment")
    public Result getCommentByUserid(@RequestParam int page,
                                     @RequestParam int size,
                                     @RequestParam int userid) {
        Result result=new Result();
        return result.success(commentService.getCommentByUserid(page,size,userid));
    }
//
//
    // 用户发表评论，发表评论，前端需要传的参数为用户号userid,用户名username，书籍ISBN,评分rate,评语content
    @PostMapping   ("/posts")
    public Result postComment(@RequestBody Comment comment) {
        System.out.println(comment);
        Integer userid = comment.getUserid();
        Long ISBN = comment.getIsbn();
        Integer rate = comment.getRate();
        String content = comment.getComment();
        Date commentdate = new Date();
        String username= comment.getUsername();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(formatter.format(date));
//        System.out.println(username + " " + password + confirmedPassword);
//        user_id,ISBN,rate,comment,comment_time
//        Comment comment = new Comment(,commentdate);
//        System.out.println(comment);
        return commentService.postComment(userid,ISBN,rate,content,username);
//        return commentService.register(email, password, confirmedPassword);
    }
    //用户自己的评论评分
    @GetMapping("/mycomment")//get路径为http://localhost:3000/comments?page=?&size=?&email=???前端传入的数据为页面page，页的大小，emial
    public Result seluserment(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam int userid){
        System.out.println(userid);
        System.out.println(page);
        System.out.println(size);
        Result result=new Result();
        return result.success(commentService.seluserment(page, size, userid));
    }
    @GetMapping("/newest")
    //最全部书籍的最新书籍，前端传入页面号和页面大小，前50条,后端给前端传的内容包括		"isbn": 9787506365437,
    //			"imgurl": "https://img1.doubanio.com/view/subject/s/public/s29053580.jpg",
    //			"username": null,
    //			"likeing": 300,
    //			"comment": "hao",
    //			"rate": 5,
    //			"bookname": "活着"
    public Result newestcomment(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size)
    {
        System.out.println(page);
        System.out.println(size);
        Result result=new Result();
        return commentService.newestbook(page,size);
    }


    @GetMapping("/hotcomment")
    //全部书籍的热门书评,前端传入页面号和页面大小,后端返回分页点赞数高的评论在前,前50条
    public Result hotcomment(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int size){
        Result result=new Result();
        return commentService.hotcomment(page,size);
    }

    @GetMapping("/bookhotcomment/{isbn}")//一本书籍中评论的最热评论
    public Result bookhotcomment(@PathVariable Long isbn,@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int size){
        Result result=new Result();
        return commentService.commentbookhot(isbn, page, size);
    }

    @GetMapping("/booknewest/{isbn}")//一本书籍中评论的最新评论
    public Result booknewestcomment(@PathVariable Long isbn,@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size){
        Result result=new Result();
        return commentService.commentbooknewest(isbn, page, size);
    }

    @PutMapping//修改评论，前端必须要传修改的书籍的isbn和用户的userid，然后传修改的评分或评语
    public Result UDcomment(@RequestBody Comment comment){
        System.out.println(comment);
        Result result=new Result();
        int m=commentService.udcomment(comment);
        if(m!=0){
            return result.success();
        }else{
            return result.error("400","failed");
        }
    }
    @DeleteMapping("/{commentid}")
    //前端的请求方式为Delete前端的请求路径为http://localhost:3000/comments/1,这个1就是代表commentid为1的书评
    public Result decomment(@PathVariable Integer commentid){
        int r=commentService.decomment(commentid);
        Result result=new Result();
        if(r!=0){
            return result.success();
        }else {
            return result.error("400","failed");
        }
    }
    @PutMapping("/{commentid}")
    //请求路径为 http://localhost:3000/comments/2，2就是commentid，
    // 进行点赞前端要传给我后端点赞的commentid是多少
    public Result likeing(@PathVariable Integer commentid){
        Result result=new Result();
        int r=commentService.likeing(commentid);
        if(r!=0){
            return result.success();
        }else {
            return result.error("400","failed");
        }
    }

    @GetMapping("good/{userid}/{commentid}")
    //请求路径为 http://localhost:3000/comments/2/2，2就是userid，2是commentid
    // 查看用户是否已经对这条信息点赞了，点赞了就返回200,没有点赞就返回400
    public Result Sellikeing(@PathVariable Integer userid,@PathVariable Integer commentid){
        Result result=new Result();
        int r=commentService.Selcomment(commentid,userid);
        if(r!=0){
            return result.success();
        }else {
            return result.error("400","没有点赞");
        }
    }
    @PutMapping("good/{userid}/{commentid}")
    //请求路径为 http://localhost:3000/comments/2/2，2就是userid，2是commentid
    // 进行点赞前端要传给我后端点赞的commentid是多少，点赞人的userid为多少
    public Result likeings(@PathVariable Integer userid,@PathVariable Integer commentid){
        Result result=new Result();
        int r=commentService.likeings(commentid,userid);
        if(r!=0){
            return result.success();
        }else {
            return result.error("400","已经点赞");
        }
    }

    @DeleteMapping ("good/{userid}/{commentid}")
    //请求路径为 http://localhost:3000/comments/2/2，2就是userid，2是commentid
    // 解除点赞前端要传给我后端点赞的commentid是多少，点赞人的userid为多少
    public Result Delikeings(@PathVariable Integer userid,@PathVariable Integer commentid){
        Result result=new Result();
        int r=commentService.Delikeing(commentid,userid);
        if(r!=0){
            return result.success();
        }else {
            return result.error("400","failed");
        }
    }

}
