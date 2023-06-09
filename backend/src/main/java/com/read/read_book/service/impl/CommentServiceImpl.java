package com.read.read_book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.read.read_book.Mapper.BookMapper;
import com.read.read_book.Mapper.CommentMapper;
import com.read.read_book.Mapper.UserMapper;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.Comment;
import com.read.read_book.pojo.User;
import com.read.read_book.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BookMapper bookMapper;

    // 书籍所有评论
    @Override
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
        Comment comment = new Comment(user_id,ISBN,rate,content,commentdate);

        //插入数据库
        commentMapper.insert(comment);

        result.put("message","success");
        return result;
    }
}
