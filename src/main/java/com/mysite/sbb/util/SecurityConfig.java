package com.mysite.sbb.util;

import com.mysite.sbb.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//스프링의 환경설정 파일임을 의미하는 어노테이션
@Configuration
// 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 어노테이션
@EnableWebSecurity
// 시큐리티를 설정하기 위한 클래스
public class SecurityConfig {

    // 유저 시큐리티 서비스와 연결 (유저이름을 가지고 비밀번호를 확인함)
    @Autowired
    private UserSecurityService userSecurityService;

    /*
    bean 어노테이션은 개발자가 직접 제어가 불가능 외부 라이브러리를 bean 으로 만들려고 할때 사용
    원래 자바는 new를 통해 객체를 생성하고 사용한다. 하지만 스프링 에서는 원하는 객체를 직접 생성하고 사용한다.
    이렇게 스프링에 의해 생성되고 관리되는 자바객체를 bean이라고 한다.
     */
    @Bean
    /*
    SecurityFilterChain
    로그인 로그아웃 등등 보안과 인증 필터가 마치 체인처럼 연결되어있어 SecurityFilterChain이라고 한다.
    http프로토콜의 요청으로 받은 헤더,쿠키,URL 등의 정보를 처리하는 HttpServletRequest와 일치할 수 있는 필터 연결을 정의
    */
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
        .을 기준으로 순서대로 정의하겠음
        http : http 시큐리티
        authorizeRequests() : HttpServletRequest를 이용함
        antMatchers() : 특정 경로를 지정
        permitAll() : 모든 사용자 접근 허용
        해석하면 http 시큐리티를 HttpServletRequest를 이용해서 모든경로(/**)에 모든 사용자가 접근하는 것을 허용한다.
        ※ 이게 없으면 스프링 시큐리티는 모든 사용자에게 인증하라고 요청함.
         */
        http.authorizeRequests().antMatchers("/**").permitAll()
                // 우리가 아는 그 and 임
                .and()
                /*
                 csrf() : csrf [처음 인증했을 때 얻은 토큰을 해커가 위조해 개인정보를 빼냄]를 말함
                 ignoringAntMatchers() : 해당 경로와 일치하는 건 무시함
                 해석하면 일치하는 경로에 csrf를 무시함.
                 -> h2콘솔은 csrf인증을 해야해서 h2콘솔로 접근시 csrf인증을 무시하는거. 
                 ※ 사실 h2로 접근하는거 아니면 이 코드는 필요 없다.
                 */
                .csrf().ignoringAntMatchers("/h2-console/**")
                // 우리가 아는 그 and 임
                .and()
                /*
                csrf() 를 무시한다 해도 제대로 사이트를 작성하지 못하는 문제 발생함.
                이를 해결하기 위해 사용하는 코드
                ※ 사실 h2로 접근하는거 아니면 이 코드는 필요 없다.
                 */
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                // 우리가 아는 그 and 임
                .and()
                /*
                formLogin() : 폼을 통해 로그인(인증)을 하겠다는 것을 선언 (로그인 하려면 입력란이 필요해서 무조건 폼 필요)
                loginPage() : 로그인 하는 페이지
                defaultSuccessUrl() : 성공시 이동하는 페이지
                해석하면 로그인을 할건데 로그인 페이지는 /user/login 이고 성공하면 /여기로 이동해라.
                */
                .formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/")
                // 우리가 아는 그 and 라고..
                .and()
                /*
                logout() : 로그아웃(인증해제)를 하겠다고 선언
                logoutRequestMatcher() : 경로와 일치하는 곳에서 로그아웃을 진행함.
                logoutSuccessUrl() : 로그아웃을 성공하면 /로 이동
                invalidateHttpSession() : 로그아웃 성공하면 세션을 지움 (세션을 통해 인증이 유지됨 즉, 세션을 지우면 인증을 지움)
                해석하면 로그웃을 할건데 /user/logout여기서 할거고 성공하면 /로 이동하고 세션은 지워.
                */
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
        ;
                // 위 작업을 build함
        return http.build();
    }

    // bean은 위에서 설명함
    @Bean
    // PasswordEncoder 는 BCryptPasswordEncoder의 인터페이스이다.
    public PasswordEncoder passwordEncoder() {
        // 해싱함수를 사용해서 비밀 번호를 암호화함 (1234 -> ASDr&&%^%^$%FDad 이런식으로) 
        return new BCryptPasswordEncoder();
    }
    
    //bean은 위에서 설명함
    @Bean
    /*
    AuthenticationManager는 인증을 말한다.
    @Bean으로 생성시 자동으로 UserSecurityService와 PasswordEncoder가 자동으로 설정되며,
    UserSecurityService에서 던져진 에러를 받는다. ("사용자가 일치하지 않습니다" 오류)
    받은 에러는 다시 login_form.html에서 처리됨.
    */
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // 인증 성공 혹은 실패에 따른 후 조취를 진행함.
        return authenticationConfiguration.getAuthenticationManager();
    }
}