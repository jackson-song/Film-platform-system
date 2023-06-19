package com.read.read_book.recommendtest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.read.read_book.Mapper.BookMapper;
import com.read.read_book.Mapper.CommentMapper;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/userbased")
    public List<Book> getUserBasedCommend() {
        List<Comment> allcomments = commentMapper.selectList(null);
//        CollaborativeFiltering collaborativeFiltering = new CollaborativeFiltering();
//        List<Integer> result = CollaborativeFiltering.recommendBooks(allcomments,1,1);
//        List<Integer> result =

        // 推荐算法返回推荐书籍的isbn
        List<Long> userBasedRecommendBookIsbn = wbCF.wrecommendBooks(allcomments,1,2);
        // 用于存储推荐书籍
        List<Book> userBasedRecommendBook = new ArrayList<>();
        // 遍历推荐算法返回的ISBN列表查询书籍,并放入Booklist
        for(Long t: userBasedRecommendBookIsbn) {
//            QueryWrapper<Book> wrapper = new QueryWrapper<>();
//            wrapper.eq(t);
//            Book tbook = bookMapper.selectbyisbn(t);//报错了
//            userBasedRecommendBook.add(tbook);
        }
        // 查询热门书籍
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("ratnum").last("limit 10");
        List<Book> hotbook = bookMapper.selectList(wrapper);
        // 放进结果List中
        userBasedRecommendBook.addAll(hotbook);

        return userBasedRecommendBook;
    }

}
