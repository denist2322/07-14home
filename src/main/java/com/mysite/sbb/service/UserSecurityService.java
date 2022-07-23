package com.mysite.sbb.service;
// 유저 보안 서비스
import com.mysite.sbb.dao.UserRepository;
import com.mysite.sbb.domain.SiteUser;
import com.mysite.sbb.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// 로그인에 핵심영역
@Service
// UserDetailsService 인터페이스를 상속 받음
public class UserSecurityService implements UserDetailsService {

    // 유저 저장소와 연결결
   @Autowired
    private UserRepository userRepository;

    @Override
    /*
    loadUserByUsername는 스프링 시큐리티가 제공하는 UserDetailsService의 메소드로 사용자 명을 통해 비밀번호를 조회함.
    때문에 매개변수로 사용자 이름을 받는 것임.
    만약 유저 이름을 찾을 수 없다면 에러(UsernameNotFoundException) 를 SecurityConfig AuthenticationManager 에게 던짐(throws)
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 입력받은 사용자 이름으로 유저(_siteUser)를 찾는다.
        Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
        // 유저를 찾지 못한경우 에러를 던짐 (단 에러 메시지 "사용자를 찾을수 없습니다."는 프론트에 표시하지 않음 (하고자 하면 할 수 있음))
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        // 유저를 찾았다면 정보를 가져옴
        SiteUser siteUser = _siteUser.get();

        //GrantedAuthority는 현재 사용자가 가지고 있는 권한을 의미함 따라서 권한을 리스트(여러개)로 받는다는 뜻임
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 만약 유저이름이 admin일 경우
        if ("admin".equals(username)) {
            /*
            관리자 권한을 추가함
            SimpleGrantedAuthority 는 권한을 저장할 저장소로 보자
            다시 말해서 authorities는 상자 SimpleGrantedAuthority는 그릇으로 그릇에 담아 상자에 둔다고 생각하면 될듯
             */
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            // 관리자가 아니면 일반 유저임
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        //
        /*
        이 User는 userdetails에서 제공하는 객체로 loadUserByUsername 메소드에서 User객체를 생성하고 값을 리턴하면
        시큐리티가 자체적으로 DB에 저장된 비밀번호와 입력된 비밀번호를 대조한다.
        */
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}
