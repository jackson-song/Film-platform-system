package com.read.read_book.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Table comment
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    Integer cid;
    Integer uid;
    String comment;
}
