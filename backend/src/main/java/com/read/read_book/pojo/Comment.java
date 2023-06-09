package com.read.read_book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// Table comment
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    String email;
    Integer userid;
    Long isbn;
    Integer rate;
    String comment;
    Date commenttime;
}
