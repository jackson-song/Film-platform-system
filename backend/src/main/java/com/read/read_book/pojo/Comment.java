package com.read.read_book.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// Table comment
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @TableId(type = IdType.AUTO)
    Integer commentid;
    Integer userid;
    Long isbn;
    Integer rate;
    String comment;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    Date commenttime;
    Long likeing;
}
