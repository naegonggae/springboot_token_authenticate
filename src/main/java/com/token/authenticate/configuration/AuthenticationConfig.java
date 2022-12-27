package com.token.authenticate.configuration;

import com.token.authenticate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 스프링시큐리티 작동시작
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final UserService userService;
    @Value("${jwt.token.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/api/v1/users/login").permitAll() // join, login은 인증을 안받아도 되게 풀어준다.
                .antMatchers(HttpMethod.POST, "/api/v1/reviews").authenticated()  // 문을 만들기 // 모든 post요청을 인증을 받아야 풀어준다. , 순서도 중요!
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt사용하는 경우 씀
                .and()
                .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class) //UserNamePasswordAuthenticationFilter적용하기 전에 JWTTokenFilter를 적용 하라는 뜻 입니다.
                // 토큰 필터는 티켓을 산거라고 보면 돼
                .build();
    }
}
//Spring Security넣기 전
//Client(User) —> API → EventHandler → Controller
//
//Spring Security넣은 후
//Client(User) —> API→ 인증계층(Filter Chain) → EventHandler → Controller
//
//인증계층 기본값 : 모든 요청을 authenticated() 인증필요로 막습니다.
//
//
//그러고나서
//.antMatchers("/api/**").permitAll()
//.antMatchers("/api/v1/users/join", "/api/v1/users/login").permitAll()
//이걸로 권한을 허용함