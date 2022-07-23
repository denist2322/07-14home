package com.mysite.sbb.domain;
// 유저 엔티티임
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
// 개체임을 선언함
@Entity
public class SiteUser {
    // id가 기본키임
    @Id
    // id는 자동으로 1씩 증가함을 알림
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 유저이름은 유일하다(중복을 허용하지 않는다.)
    @Column(unique = true)
    private String username;

    private String password;
    // 이메일은 유일하다(중복을 허용하지 않는다.)
    @Column(unique = true)
    private String email;
}