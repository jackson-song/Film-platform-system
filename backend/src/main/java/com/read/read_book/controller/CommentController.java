package com.read.read_book.controller;

import com.read.read_book.dto.logindto;
import com.read.read_book.pojo.Comment;
import com.read.read_book.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //用户自己的评论评分
    @PostMapping ("user/myComment")
    public List<Comment> seluserment( @RequestBody logindto logindto){
        String email =logindto.getEmail();
        System.out.println(email);
        return commentService.seluserment(email);
    }

}
