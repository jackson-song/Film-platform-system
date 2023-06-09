package com.read.read_book.controller;

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
public class CommentController {

    @Autowired
    CommentService commentService;

    // 书的所有评论
    @RequestMapping("/BookComment/")
    public List<Comment> getBookByBookname(@RequestParam Long isbn) {
        return commentService.getCommentByISBN(isbn);
    }

    // 用户的所有评论
    @RequestMapping("/allComment/")
    public List<Comment> getCommentByUserid(@RequestParam Integer userid) {
        return commentService.getCommentByUserid(userid);
    }


    // 发表评论
    @RequestMapping("/postacomment/")
    public Map<String, String> postComment(@RequestParam Map<String, String> map) {
        Integer user_id = Integer.valueOf(map.get("user_id"));
        Long ISBN = Long.valueOf(map.get("ISBN"));
        Integer rate = Integer.valueOf(map.get("rate"));
        String content = map.get("content");

        Date commentdate = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(formatter.format(date));

//        System.out.println(username + " " + password + confirmedPassword);
//        user_id,ISBN,rate,comment,comment_time
//        Comment comment = new Comment(,commentdate);
//        System.out.println(comment);
        return commentService.postComment(user_id,ISBN,rate,content);
//        return commentService.register(email, password, confirmedPassword);
    }
    //用户自己的评论评分
    @PostMapping ("user/myComment")
    public List<Comment> seluserment( @RequestBody logindto logindto){
        String email =logindto.getEmail();
        System.out.println(email);
        return commentService.seluserment(email);

    }

}
