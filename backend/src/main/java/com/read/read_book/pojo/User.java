package com.read.read_book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Table user
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userid;
    private String username;
    private String pwd;
    private String gender;
    private Integer age;
    private String email;
    private String introduction;
    private Integer Upower;
    private Integer state;
}
