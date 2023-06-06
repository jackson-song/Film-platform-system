package com.read.read_book.controller;

import com.read.read_book.pojo.Comment;
import com.read.read_book.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    // 某本书的所有评论
    @RequestMapping("/getbookallcomment/{isbn}")
    public List<Comment> getBookByBookname(@PathVariable Long isbn) {
        System.out.println("jinrucontroller");
        return commentService.getCommentByisbn(isbn);
    }

    // 某用户的所有评论
    @RequestMapping("/getuserallComment/{userid}")
    public List<Comment> getBookByBookname(@PathVariable Integer userid) {
        return commentService.getCommentByuserid(userid);
    }

}
