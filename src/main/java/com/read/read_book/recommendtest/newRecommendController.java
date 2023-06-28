/*
package com.read.read_book.recommendtest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.read.read_book.Mapper.BookMapper;
import com.read.read_book.Mapper.CommentMapper;
import com.read.read_book.common.Result;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
//@RequestMapping("/recommend")
public class newRecommendController {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    BookMapper bookMapper;

    @RequestMapping("/userbased")
    public List<Book> getUserBasedCommend(@RequestParam int userid) {
//    public Result getUserBasedCommend(@RequestParam int userid) {
        List<Comment> allcomments = commentMapper.selectList(null);
//        CollaborativeFiltering collaborativeFiltering = new CollaborativeFiltering();
//        List<Integer> result = CollaborativeFiltering.recommendBooks(allcomments,1,1);
//        List<Integer> result =

        // 推荐算法返回推荐书籍的isbn
        List<Long> userBasedRecommendBookIsbn = newwbCF.wrecommendBooks(allcomments,userid,10);
        // 用于存储推荐书籍
        List<Book> userBasedRecommendBook = new ArrayList<>();
        // 遍历推荐算法返回的ISBN列表查询书籍,并放入Booklist
        for(Long t: userBasedRecommendBookIsbn) {
//            QueryWrapper<Book> wrapper = new QueryWrapper<>();
//            wrapper.eq(t);
            Book tbook = bookMapper.selectbyisbn(t);//报错了
            userBasedRecommendBook.add(tbook);
        }

        // 推荐结果书籍小于10本,查询热门书籍
        if(userBasedRecommendBook.size() < 10) {
            QueryWrapper<Book> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("ratnum").last("limit 10");
            List<Book> hotbook = bookMapper.selectList(wrapper);
            // 放进结果List中
            userBasedRecommendBook.addAll(hotbook);
        }

        // 截取10本书
        userBasedRecommendBook = userBasedRecommendBook.subList(0,10);

//        System.out.println(userBasedRecommendBook.size());

        Result result = new Result();


        // 两种返回结果
//        return result.success(userBasedRecommendBook);
        return userBasedRecommendBook;
    }

    //30本
    @RequestMapping("/userbasedall")
    public List<Book> getUserBasedAllCommend(@RequestParam int userid) {
//    public Result getUserBasedAllCommend(@RequestParam int userid) {
        List<Comment> allcomments = commentMapper.selectList(null);
//        CollaborativeFiltering collaborativeFiltering = new CollaborativeFiltering();
//        List<Integer> result = CollaborativeFiltering.recommendBooks(allcomments,1,1);
//        List<Integer> result =

        // 推荐算法返回推荐书籍的isbn
        List<Long> userBasedRecommendBookIsbn = newwbCF.wrecommendBooks(allcomments,userid,30);
        // 用于存储推荐书籍
        List<Book> userBasedRecommendBook = new ArrayList<>();
        // 遍历推荐算法返回的ISBN列表查询书籍,并放入Booklist
        for(Long t: userBasedRecommendBookIsbn) {
//            QueryWrapper<Book> wrapper = new QueryWrapper<>();
//            wrapper.eq(t);
            Book tbook = bookMapper.selectbyisbn(t);//报错了
            userBasedRecommendBook.add(tbook);
        }

        // 推荐结果书籍小于30本,查询热门书籍
        if(userBasedRecommendBook.size() < 30) {
            QueryWrapper<Book> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("ratnum").last("limit 30");
            List<Book> hotbook = bookMapper.selectList(wrapper);
            // 放进结果List中
            userBasedRecommendBook.addAll(hotbook);
        }

        // 截取10本书
        userBasedRecommendBook = userBasedRecommendBook.subList(0,30);

//        System.out.println(userBasedRecommendBook.size());

        Result result = new Result();


        // 两种返回结果
//        return result.success(userBasedRecommendBook);
        return userBasedRecommendBook;
    }

}
*/
