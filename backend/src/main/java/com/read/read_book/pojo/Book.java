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
    String Book_name;
    String author;
    String press;
    double Book_grading;
    Date Publication_time;
    String synopsis;
}
