package com.read.read_book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.awt.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Userdto {
        private Integer id;
        private String username;
        private String email;
        private String code;
        private String password;
        private String nickname;
        private String avatarUrl;
        private String token;
        private String role;
        private List<Menu> menus;
    }
