package com.read.read_book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class bookpage {
    Integer pagenum;
    Integer pagesize;
    Integer ISBN;
    String Bookname;
    String author;
    String booktypename;
}
