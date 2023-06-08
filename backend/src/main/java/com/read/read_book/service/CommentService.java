package com.read.read_book.service;

import com.read.read_book.pojo.Comment;

import java.util.List;

public interface CommentService {

    //书籍评论(未实现分页)
    public List<Comment> getCommentByISBN(Long isbn);

    //用户评论(未实现分页)
    public List<Comment> getCommentByUserid(Integer userid);

    //热门评论


    //最新评论


    //发表评论


    //修改评论


    //删除评论,后台功能


    //查询个人书评
    public List<Comment> seluserment(String emial);

}
