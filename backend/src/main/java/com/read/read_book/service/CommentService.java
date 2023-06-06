package com.read.read_book.service;

import com.read.read_book.pojo.Comment;

import java.util.List;

public interface CommentService {

    //get all comments by ISBN
    public List<Comment> getCommentByisbn(Long isbn);

    //get all comments by userid
    public List<Comment> getCommentByuserid(Integer userid);
}
