package com.read.read_book.recommendtest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.read.read_book.Mapper.BookMapper;
import com.read.read_book.Mapper.CommentMapper;
//import com.read.read_book.Mapper.LikeTypeMapper;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
//@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    BookMapper bookMapper;

//    @Autowired
//    LikeTypeMapper likeTypeMapper;

/*
    @RequestMapping("/liketype/{userid}")
    public List<String> getliketype(@PathVariable int userid) {
        return likeTypeMapper.getuserliketype(userid);
    }
*/

    @RequestMapping("/userbased")
    public List<Book> getUserBasedCommend(@RequestParam int userid) {
//        CollaborativeFiltering collaborativeFiltering = new CollaborativeFiltering();
//        List<Integer> result = CollaborativeFiltering.recommendBooks(allcomments,1,1);
//        List<Integer> result =

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);
        List<Comment> usercomments = commentMapper.selectList(queryWrapper);

        System.out.println(usercomments);

        List<Book> userBasedRecommendBook = new ArrayList<>();

        // 用户有评论则进行基于用户的协同过滤推荐
        if(usercomments != null && usercomments.size() != 0) {
            List<Comment> allcomments = commentMapper.selectList(null);
            // 推荐算法返回推荐书籍的isbn
            List<Long> userBasedRecommendBookIsbn = newwbCF.wrecommendBooks(allcomments,userid,10);
            // 用于存储推荐书籍
            // 遍历推荐算法返回的ISBN列表查询书籍,并放入Booklist
            for(Long t: userBasedRecommendBookIsbn) {
//            QueryWrapper<Book> wrapper = new QueryWrapper<>();
//            wrapper.eq(t);
                Book tbook = bookMapper.selectbyisbn(t);//报错了
                userBasedRecommendBook.add(tbook);
            }
        } else
            System.out.println("用户没评论");

        // 查询热门书籍
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("ratnum").last("limit 10");
        List<Book> hotbook = bookMapper.selectList(wrapper);
        // 放进结果List中
        userBasedRecommendBook.addAll(hotbook);

        userBasedRecommendBook = userBasedRecommendBook.subList(0,10);

        return userBasedRecommendBook;
    }

    @RequestMapping("/userbasedall")
    public List<Book> getUserBasedAllCommend(@RequestParam int userid) {
//    public Result getUserBasedAllCommend(@RequestParam int userid) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);
        List<Comment> usercomments = commentMapper.selectList(queryWrapper);

        System.out.println(usercomments);

        // 用于存储推荐书籍
        List<Book> userBasedRecommendBook = new ArrayList<>();

        // 用户有评论则进行基于用户的协同过滤推荐
        if(usercomments != null && usercomments.size() != 0) {
            List<Comment> allcomments = commentMapper.selectList(null);
            // 推荐算法返回推荐书籍的isbn
            List<Long> userBasedRecommendBookIsbn = newwbCF.wrecommendBooks(allcomments,userid,30);
            // 遍历推荐算法返回的ISBN列表查询书籍,并放入Booklist
            for(Long t: userBasedRecommendBookIsbn) {
//            QueryWrapper<Book> wrapper = new QueryWrapper<>();
//            wrapper.eq(t);
                Book tbook = bookMapper.selectbyisbn(t);//报错了
                userBasedRecommendBook.add(tbook);
            }
        } else
            System.out.println("用户没评论");

        // 推荐结果书籍小于30本,查询热门书籍
        if(userBasedRecommendBook.size() < 30) {
            QueryWrapper<Book> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("ratnum").last("limit 30");
            List<Book> hotbook = bookMapper.selectList(wrapper);
            // 放进结果List中
            userBasedRecommendBook.addAll(hotbook);
        }

        // 截取30本书
        userBasedRecommendBook = userBasedRecommendBook.subList(0,30);

//        System.out.println(userBasedRecommendBook.size());

//        Result result = new Result();


        // 两种返回结果
//        return result.success(userBasedRecommendBook);
        return userBasedRecommendBook;
    }

}
