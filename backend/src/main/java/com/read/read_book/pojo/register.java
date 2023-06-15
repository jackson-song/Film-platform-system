package com.read.read_book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class register {

    String email;
//    @NotBlank(message = "密码不能为空")
//    @Pattern(regexp="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$",message = "密码必须是6~18位之间的数字或字母")

    String pwd;
    String confirmedPwd;
}
