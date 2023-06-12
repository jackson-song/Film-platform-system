package com.read.read_book.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class GetSession {
    public static HttpSession getSession(){
        HttpServletRequest request = GetRequest.getRequest();
        HttpSession session = request.getSession();
        return session;
    }

}
