package com.mysite.sbb.dao;

import com.mysite.sbb.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Integer> {
}
