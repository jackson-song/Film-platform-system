package com.read.read_book.service;

import java.util.Map;

public interface UserService {
    public Map<String, String> register(String username, String password, String confirmedPassword);
}
