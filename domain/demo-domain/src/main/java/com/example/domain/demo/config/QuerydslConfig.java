package com.example.domain.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Configuration(BeanName.PREFIX + "QuerydslConfig")
public class QuerydslConfig {

	public static final String JPA_QUERY_FACTORY = BeanName.PREFIX + "JPAQueryFactory";

	@PersistenceContext(unitName = JpaConfig.PERSISTENCE_UNIT_NAME)
	private EntityManager entityManager;

	@Bean(JPA_QUERY_FACTORY)
	public JPAQueryFactory jpaQueryFactory() {
		return new JPAQueryFactory(entityManager);
	}
}