package com.read.read_book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class loginstatistic2 //活跃度（登录次数）最多的前5个用户的用户名username和（活跃度）登录次数times
{
    String username;
    Integer times;
}
