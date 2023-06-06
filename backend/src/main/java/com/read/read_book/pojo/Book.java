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
    Long ISBN;
    String Bookname;
    String author;
    String press;
    Double bookgrading;
    Date Publicationtime;
    String synopsis;
}
