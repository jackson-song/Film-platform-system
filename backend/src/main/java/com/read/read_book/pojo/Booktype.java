package com.read.read_book.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("type")
public class Booktype {
    Long isbn;
    String Bookname;
    String author;
    String press;
    Double Bookgrading;
    int ratnum;
    String imgurl;
    //    @DateTimeFormat(pattern="yyyy-MM-dd")
    String Publicationtime;
    String synopsis;
    String type;


}
