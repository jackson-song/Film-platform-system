package com.read.read_book.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.read.read_book.pojo.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bookshelf {
    Integer userid;
    Long isbn;
    @TableField(exist = false)
    private List<Book> books;
}
