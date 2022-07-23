package com.mysite.sbb.service;
// 유저 서비스
import com.mysite.sbb.dao.UserRepository;
import com.mysite.sbb.domain.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    // 회원 가입 진행
    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        /*
        PasswordEncoder를 SecurityConfig에서 @Bean을 통해 생성했음.
        PasswordEncoder에는 다음과 같이 3가지의 옵션이 있음
        1. String encode(CharSequence rawPassword) : 비밀번호를 단방향 암호화 한다.
        2. boolean matches(CharSequence rawPassword, String encodedPassword)
        : 암호화 되지 않은 비밀번호와 암호화된 비밀번호가 일치하는지 비교한다.
        3. default boolean upgradeEncoding(String encodedPassword) { return false; };
        : 암호화된 비밀번호를 다시 암호화하고자 할 경우 true를 리턴 아니면 false 리턴.
        여기서는 위 세가지 옵션중 1번 encode를 사용해서 암호화를 진행항 것임.
         */
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }
}
