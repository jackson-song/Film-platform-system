package com.read.read_book.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.read.read_book.common.Result;
import com.read.read_book.pojo.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {
//
    //书籍评论(实现分页)
    public Page<Comment> getCommentByISBN(int page,int size,Long isbn);
//
    //用户评论(实现分页)
    public Page<Comment> getCommentByUserid(int page,int size,Integer userid);
//
//    //热门书评
    Result hotcomment(int page,int size);
//
//    //最新书评
    Result newestbook(int page,int size);


    Result commentbooknewest(Long isbn,int page,int size);//一本书籍内评论的最新

    Result commentbookhot(Long isbn,int page,int size);//一本书籍内的热门评论

//
//    //发表评论
    public Result postComment(Integer userid, Long ISBN, Integer rate, String content,String username);

    //修改评论
    public Integer udcomment(Comment comment);

    //删除评论,后台功能
    public Integer decomment(Integer commentid);

    //评论点赞
    public Integer likeing(Integer commentid);

    //查询个人书评
    public Page<Comment> seluserment(int pagenum,int size,int userid);

    public Comment commentbyuseridandisbn(Integer userid,Long isbn);

    //点赞
    Integer likeings(Integer commentid, Integer userid);


    //解除点赞
    Integer Delikeing(Integer commentid, Integer userid);

    //查看用户是否点赞了
    Integer Selcomment(Integer commentid, Integer userid);
}
