package com.read.read_book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.read.read_book.Mapper.BookMapper;
import com.read.read_book.Mapper.CommentMapper;
import com.read.read_book.Mapper.LikeingsMapper;
import com.read.read_book.Mapper.UserMapper;
import com.read.read_book.common.Result;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.Comment;
import com.read.read_book.pojo.Likeings;
import com.read.read_book.pojo.User;
import com.read.read_book.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    LikeingsMapper likeingsMapper;

    // 书籍所有评论
    @Override
    public Page<Comment> getCommentByISBN(int page,int size,Long isbn) {
        Page<Comment> page1=new Page<>(page,size);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isbn",isbn);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return commentMapper.selectPage(page1,queryWrapper);
    }

    // 用户所有评论
    @Override
    public Page<Comment> getCommentByUserid(int page,int size,Integer userid) {
        Page<Comment> page1=new Page<>(page,size);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userid);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return commentMapper.selectPage(page1,queryWrapper);
    }

    @Override//热门评论
    public Result hotcomment(int page, int size) {
//        Page<Comment> page1=new Page<>(page,size);
//        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
//        wrapper.orderByDesc("likeing");
//        return commentMapper.selectPage(page1,wrapper);
        Result result=new Result();
        page=(page-1)*size;
        int total=commentMapper.total();
        if(total>50){
            return result.success(commentMapper.commenthot(page,size),50);}
        else {
            return result.success(commentMapper.commenthot(page,size),total);
        }
    }

    @Override//最新书评
    public Result newestbook(int page, int size) {
//        Page<Comment> page1=new Page<>(page,size);
//        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
//        wrapper.orderByDesc("commenttime");
//        Wrappers.<Book>query().orderByDesc("Publicationtime").last("limit 50")
//        wrapper.like("Bookname",text).or().like("author", text).or().like("isbn",text);
//        List<Book> books = bookMapper.selectList(Wrappers.<Book>query().orderByDesc("Publicationtime").last("limit 50"));
        Result result=new Result();
        page=(page-1)*size;
        int total=commentMapper.total();
        if(total>50){
        return result.success(commentMapper.commentnewest(page,size),50);}
        else {
            return result.success(commentMapper.commentnewest(page,size),total);
        }
    }

    @Override
    public Result commentbooknewest(Long isbn, int page, int size) {
        Result result=new Result();
        page=(page-1)*size;
        int total=commentMapper.totalbook(isbn);
        if(total>50){
            return result.success(commentMapper.commentbooknewest(isbn,page,size),50);}
        else {
            return result.success(commentMapper.commentbooknewest(isbn,page,size),total);
    }}

    @Override
    public Result commentbookhot(Long isbn, int page, int size) {
        Result result=new Result();
        page=(page-1)*size;
        int total=commentMapper.totalbook(isbn);
        if(total>50){
            return result.success(commentMapper.commentbookhot(isbn,page,size),50);}
        else {
            return result.success(commentMapper.commentbookhot(isbn,page,size),total);}
    }


    // 发表评论
    @Override
    public Result postComment(Integer userid, Long ISBN, Integer rate, String content,String username) {
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
       comment.setUsername(username);
       comment.setComment(content);
       comment.setCommenttime(commentdate);
       comment.setRate(rate);
       comment.setLikeing(0L);
//       comment.setLikeing(0L);
        //插入数据库
        commentMapper.insert(comment);

        result.put("message","success");
        return result1.success();
    }

    @Override//修改书评
    public Integer udcomment(Comment comment) {
        return commentMapper.updatecomment(comment);
    }

    @Override//删除书评
    public Integer decomment(Integer commentid) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("commentid",commentid);
        return commentMapper.delete(wrapper);
    }

    //
    @Override//用户自己的评论评分
    public Page<Comment> seluserment(int page,int size,int userid) {
        Page<Comment> page1 = new Page(page, size);
        System.out.println(page1);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("userid",userid);
        System.out.println(wrapper);
        return commentMapper.selectPage(page1,wrapper);
    }

    @Override
    public Comment commentbyuseridandisbn(Integer userid,Long isbn) {
//        int userid=comment.getUserid();
//        long isbn=comment.getIsbn();
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("userid",userid);
        wrapper.eq("isbn",isbn);
        System.out.println(userid+isbn);
        return commentMapper.selectOne(wrapper);
    }


    @Override//点赞
    public Integer likeing(Integer commentid) {
        Comment comment= commentMapper.secomment(commentid);
        Long likeing=comment.getLikeing();
        Long newlikeing=likeing+1;
        return commentMapper.addlike(newlikeing,commentid);
    }


    @Override//点赞
    public Integer likeings(Integer commentid, Integer userid) {
        if(likeingsMapper.getlike(userid,commentid)==null){
            Likeings likeings=new Likeings();
            likeings.setUserid(userid);
            likeings.setCommentid(commentid);
        Comment comment= commentMapper.secomment(commentid);
        Long likeing=comment.getLikeing();
        Long newlikeing=likeing+1;
        likeingsMapper.insert(likeings);
        return commentMapper.addlike(newlikeing,commentid);
        }
        else return 0;
    }


    @Override//解除点赞
    public Integer Delikeing(Integer commentid,Integer userid) {
            Comment comment= commentMapper.secomment(commentid);
            Long likeing=comment.getLikeing();
            Long newlikeing=likeing-1;
            QueryWrapper<Likeings> wrapper = new QueryWrapper<>();
            wrapper.eq("userid",userid);
            wrapper.eq("commentid",commentid);
            likeingsMapper.delete(wrapper);
            return commentMapper.uplikeing(newlikeing,commentid);
    }


    @Override//查看用户是否点赞了
    public Integer Selcomment(Integer commentid,Integer userid) {
        int result;
       if(likeingsMapper.getlike(userid,commentid)==null) {
       result=0;
       return result;
       }else {
           result=1;
           return result;
       }
       }
}
