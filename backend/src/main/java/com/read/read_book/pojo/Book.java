package com.read.read_book.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//Table Book
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("book")
public class Book {

    Integer isbn;
    String Bookname;
    String author;
    String press;
    Double Bookgrading;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    Date Publicationtime;
    String synopsis;
}
