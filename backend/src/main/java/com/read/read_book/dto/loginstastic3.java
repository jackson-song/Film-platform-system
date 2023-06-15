package com.read.read_book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class loginstastic3 //统计近一年中每一个月的活跃度，也就是每一个月有多少人登录了，time为时间,times为次数
{
    String time;
    String times;//次数
//    String year_month;
//    String month;
}
