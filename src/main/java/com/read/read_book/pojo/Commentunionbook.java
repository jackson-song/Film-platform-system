package com.read.read_book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commentunionbook {
    Long isbn;
    String Bookname;
    String imgurl;
    String username;
    Long likeing;
    String comment;
    Integer rate;
}
