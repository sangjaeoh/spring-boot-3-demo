package com.example.domain.demo.test;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.demo.config.JpaConfig;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;

@Component
public class DemoDomainDatabaseCleaner implements InitializingBean {

	private List<String> tableNames;

	@PersistenceContext(unitName = JpaConfig.PERSISTENCE_UNIT_NAME)
	private EntityManager entityManager;

	@Override
	public void afterPropertiesSet() throws Exception {
		tableNames = entityManager.getMetamodel().getEntities().stream()
			.map(entityType -> entityType.getJavaType().getDeclaredAnnotation(Table.class))
			.filter(tableMetaData -> tableMetaData != null)
			.map(Table::name)
			.toList();
	}

	@Transactional
	public void clean() {
		// 쓰기 지연 SQL 저장소 비우기
		entityManager.flush();

		// 참조 무결성 제약 조건 임시 해제
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

		// 모든 테이블 데이터 삭제 (TRUNCATE)
		for (String tableName : tableNames) {
			entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
			entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN ID RESTART WITH 1")
				.executeUpdate();
		}

		// 참조 무결성 제약 조건 활성화
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();

		// 기본 데이터 생성
		this.defaultInsert();

	}

	private void defaultInsert() {
		entityManager.createNativeQuery("insert into authorities (role) values ('ADMIN')").executeUpdate();
		entityManager.createNativeQuery("insert into authorities (role) values ('COMMON')").executeUpdate();
	}

}
