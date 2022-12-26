package com.token.authenticate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String login(String userName, String password) {
        return "토큰이 발행되었습니다.";
    }
}
