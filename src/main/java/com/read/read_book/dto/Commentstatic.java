package com.read.read_book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Commentstatic //对应统计每一个类别的评论总数的视图
{
    String type;
    Integer comments;
}
