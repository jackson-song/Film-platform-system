package com.read.read_book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recoverpwddto//找回密码
{
    private String pwd;
    private String confirmpwd;
    private String email;
}
