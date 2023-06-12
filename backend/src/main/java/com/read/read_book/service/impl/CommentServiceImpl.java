package com.read.read_book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.read.read_book.Mapper.BookMapper;
import com.read.read_book.Mapper.CommentMapper;
import com.read.read_book.Mapper.UserMapper;
import com.read.read_book.common.Result;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.Comment;
import com.read.read_book.pojo.User;
import com.read.read_book.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BookMapper bookMapper;
//
    // 书籍所有评论
    @Override
    public Page<Comment> getCommentByISBN(int page,int size,Long isbn) {
        Page<Comment> page1=new Page<>(page,size);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isbn",isbn);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return commentMapper.selectPage(page1,queryWrapper);
    }
//
    // 用户所有评论
    @Override
    public Page<Comment> getCommentByUserid(int page,int size,Integer userid) {
        Page<Comment> page1=new Page<>(page,size);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userid);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return commentMapper.selectPage(page1,queryWrapper);
    }

    @Override
    public Page<Comment> hotcomment(int page, int size) {
        return null;
    }

    @Override
    public Page<Comment> newestbook(int page, int size) {
        Page<Comment> page1=new Page<>(page,size);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("commenttime");
//        Wrappers.<Book>query().orderByDesc("Publicationtime").last("limit 50")
//        wrapper.like("Bookname",text).or().like("author", text).or().like("isbn",text);
//        List<Book> books = bookMapper.selectList(Wrappers.<Book>query().orderByDesc("Publicationtime").last("limit 50"));
        return commentMapper.selectPage(page1,wrapper);
    }

    //
    // 发表评论
    @Override
    public Result postComment(Integer userid, Long ISBN, Integer rate, String content) {
        Map<String,String> result = new HashMap<>();
        Result result1=new Result();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userid", userid);
        List<User> users = userMapper.selectList(userQueryWrapper);
        if (users.isEmpty()) {
            result.put("message", "用户不存在");
            return result1.error("400", "用户不存在");
        }
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.eq("isbn",ISBN);
        List<Book> book = bookMapper.selectList(bookQueryWrapper);
        if(book == null) {
            result.put("message", "错误的ISBN或该书不在数据库中");
            return result1.error("400","错误的ISBN或该书不在数据库中");
        }

        if(!(rate >=0 && rate <=10)) {
            result.put("message", "评分超出范围");
            return result1.error("400","评分超出范围");
        }

        if(content == null || content.length() == 0) {
            result.put("message","评论不能为空!");
            return result1.error("400","评论不能为空!");
        }

        // 评论时间处理
        Date commentdate = new Date();
//        Comment comment = new Comment(null,userid,ISBN,rate,content,commentdate);
       Comment comment=new Comment();
       comment.setIsbn(ISBN);
       comment.setUserid(userid);
       comment.setComment(content);
       comment.setCommenttime(commentdate);
       comment.setRate(rate);
       comment.setLikeing(0);
        //插入数据库
        commentMapper.insert(comment);

        result.put("message","success");
        return result1.success();
    }

    @Override
    public Integer udcomment(Comment comment) {
        return commentMapper.updatecomment(comment);
    }

    @Override
    public Integer decomment(Integer commentid) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("commentid",commentid);
        return commentMapper.delete(wrapper);
    }

    //
    @Override
    public Page<Comment> seluserment(int page,int size,int userid) {
        Page<Comment> page1 = new Page(page, size);
        System.out.println(page1);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userid);
        return commentMapper.selectPage(page, wrapper);
    }

    public List<Comment> getCommentByISBN(Long isbn) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isbn",isbn);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return comments;
    }

    // 用户所有评论
    @Override
    public List<Comment> getCommentByUserid(Integer userid) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userid);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return comments;
    }

    // 发表评论
    @Override
    public Map<String, String> postComment(Integer user_id, Long ISBN, Integer rate, String content) {
        Map<String,String> result = new HashMap<>();

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userid", user_id);
        List<User> users = userMapper.selectList(userQueryWrapper);
        if (users.isEmpty()) {
            result.put("message", "用户不存在");
            return result;
        }

        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.eq("isbn",ISBN);
        Book book = bookMapper.selectOne(bookQueryWrapper);
        if(book == null) {
            result.put("message", "错误的ISBN或该书不在数据库中");
            return result;
        }

        if(!(rate >=0 && rate <=5)) {
            result.put("message", "评分超出范围");
            return result;
        }

        if(content == null || content.length() == 0) {
            result.put("message","评论不能为空!");
            return result;
        }

        // 评论时间处理
        Date commentdate = new Date();
        Comment comment = new Comment(null, user_id, ISBN, rate, content, commentdate);

        //插入数据库
        commentMapper.insert(comment);

        result.put("message","success");
        return result;
    }

    @Override
    public List<Comment> seluserment(String emial) {
        String email2 = emial;
        List<Comment> comments=commentMapper.seluserment(email2);
        return comments;
    }
}
