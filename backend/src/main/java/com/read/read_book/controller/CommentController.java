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
    @GetMapping("BookComment")
    public Result getBookByBookname(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam Long isbn) {
        Result result=new Result();
        return result.success(commentService.getCommentByISBN(page, size, isbn));
    }

    // 用户的所有评论
    @GetMapping("/allComment")
    public Result getCommentByUserid(@RequestParam int page,
                                     @RequestParam int size,
                                     @RequestParam int userid) {
        Result result=new Result();
        return result.success(commentService.getCommentByUserid(page,size,userid));
    }
//
//
    // 发表评论
    @PostMapping   ("/post")
    public Result postComment(@RequestBody Comment comment) {
        System.out.println(comment);
        Integer userid = comment.getUserid();
        Long ISBN = comment.getIsbn();
        Integer rate = comment.getRate();
        String content = comment.getComment();
        Date commentdate = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(formatter.format(date));

//        System.out.println(username + " " + password + confirmedPassword);
//        user_id,ISBN,rate,comment,comment_time
//        Comment comment = new Comment(,commentdate);
//        System.out.println(comment);
        return commentService.postComment(userid,ISBN,rate,content);
//        return commentService.register(email, password, confirmedPassword);
    }
    //用户自己的评论评分
    @GetMapping("/mycomment")//get路径为http://localhost:3000/comments?page=?&size=?&email=???前端传入的数据为页面page，页的大小，emial
    public Result seluserment(@RequestParam int page,
                              @RequestParam int size,
                              @RequestParam int userid){
        System.out.println(userid);
        System.out.println(page);
        System.out.println(size);
        Result result=new Result();
        return result.success(commentService.seluserment(page, size, userid));
    }
    @GetMapping("/newest")//最新书籍，前端传入页面号和页面大小
    public Result newestcomment(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size)
    {
        System.out.println(page);
        System.out.println(size);
        Result result=new Result();
        return result.success(commentService.newestbook(page,size));
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
    //前端的请求方式为Delete
    public Result decomment(@PathVariable Integer commentid){
        int r=commentService.decomment(commentid);
        Result result=new Result();
        if(r!=0){
            return result.success();
        }else {
            return result.error("400","failed");
        }
    }
}
