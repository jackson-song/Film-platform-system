package com.read.read_book.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//Table Book
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    Integer isbn;
    String bookname;
    String author;
    String press;
    Double bookgrading;
    Date publicationtime;
    String synopsis;
}
