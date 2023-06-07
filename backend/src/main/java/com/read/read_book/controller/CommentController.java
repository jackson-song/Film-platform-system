package com.read.read_book.controller;

import com.read.read_book.pojo.Comment;
import com.read.read_book.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping("/myComment/")
    public List<Comment> getCommentByUserid(@RequestParam Integer userid) {
        return commentService.getCommentByUserid(userid);
    }

}
