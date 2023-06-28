package com.read.read_book.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class loginstastic  //近一周内每一天的登录人数（活跃人数）
{
    Integer times;//次数
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    Date logintime;//近一周时间
}
