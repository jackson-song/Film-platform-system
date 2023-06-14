package com.read.read_book.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("type_copy1")
public class BookBooktype {
    int isbn;
    int type;
    @TableField(exist = false)
    private List<Book> books;
}
