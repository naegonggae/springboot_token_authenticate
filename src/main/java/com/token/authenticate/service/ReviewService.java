package com.token.authenticate.service;

import com.token.authenticate.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    public String write(String userName) {
        return userName+"님의 리뷰가 등록되었습니다.";
    }
}
