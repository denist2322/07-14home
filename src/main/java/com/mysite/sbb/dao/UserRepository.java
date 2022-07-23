package com.mysite.sbb.dao;

import com.mysite.sbb.domain.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    // 이름을 통해 DB에서 정보를 가져 오겠다.
    Optional<SiteUser> findByusername(String username);
}