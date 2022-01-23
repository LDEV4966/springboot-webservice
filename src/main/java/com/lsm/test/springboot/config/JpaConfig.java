package com.lsm.test.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing  // JPA Auditing 활성화 -> Spring Data Jpa가 제공하는 엔티티의 변화를 추적하는 Audit 기능
public class JpaConfig {
}
