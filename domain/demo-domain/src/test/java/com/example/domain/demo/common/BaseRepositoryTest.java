package com.example.domain.demo.common;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.example.domain.demo.config.DataSourceConfig;
import com.example.domain.demo.config.FlywayConfig;
import com.example.domain.demo.config.JpaConfig;
import com.example.domain.demo.config.QuerydslConfig;

// import static org.assertj.core.api.Assertions.*;
// import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {DataSourceConfig.class, JpaConfig.class, QuerydslConfig.class, FlywayConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class BaseRepositoryTest {
}
