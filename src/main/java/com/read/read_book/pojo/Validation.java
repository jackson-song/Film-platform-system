package com.read.read_book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Validation {
    Integer userid;
    String email;
    String code;
    Date time;
    Integer type;
}
