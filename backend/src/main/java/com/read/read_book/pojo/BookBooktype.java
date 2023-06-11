package com.read.read_book.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("book_booktype")
public class BookBooktype {
    int isbn;
    int  booktypeid;
    @TableField(exist = false)
    private Book book;
}
