package com.read.read_book.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Table user
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId(type = IdType.AUTO)
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
