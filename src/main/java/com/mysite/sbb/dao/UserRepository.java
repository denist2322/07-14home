package com.mysite.sbb.dao;

import com.mysite.sbb.domain.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
}