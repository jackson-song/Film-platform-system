package com.read.read_book.service;

import com.read.read_book.pojo.Comment;

import java.util.List;

public interface CommentService {

    //get all comments by ISBN
    public List<Comment> getCommentByISBN(Long isbn);

    //get all comments by userid
    public List<Comment> getCommentByUserid(Integer userid);
}
