package com.example.reviewMovie.database.config;


	import java.sql.SQLException;
	import java.util.Map;

	import javax.persistence.EntityManagerFactory;

	import org.apache.commons.collections4.map.HashedMap;
	import org.springframework.beans.factory.annotation.Qualifier;
	import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
	import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.context.annotation.Primary;
	import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
	import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
	import org.springframework.orm.jpa.JpaTransactionManager;
	import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
	import org.springframework.transaction.PlatformTransactionManager;
	import org.springframework.transaction.annotation.EnableTransactionManagement;

	import com.zaxxer.hikari.HikariDataSource;

	import lombok.extern.slf4j.Slf4j;

	@Configuration
	@EnableTransactionManagement
	@EnableJpaRepositories(entityManagerFactoryRef = "localEntityManagerFactory",
	                transactionManagerRef = "localTransactionManager", basePackages = "com.example.reviewMovie.repo")
	@Slf4j
	public class LocalDatabaseConfig  extends DatabaseConfig {

		@Primary
		@Bean
		public DataSourceProperties dataSourceProperties() {
			return new DataSourceProperties();
		}

		@Primary
		@Bean
		public HikariDataSource dataSource() {

			return super.dataSource();
		}

		@Primary
		@Bean(name = "localEntityManagerFactory")
		public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) throws SQLException {
			LocalContainerEntityManagerFactoryBean build =
			                builder.dataSource(dataSource()).packages("com.example.reviewMovie.entity")
			                                .persistenceUnit("").properties(jpaProperties()).build();
			System.out.println(build.getDataSource().getConnection().getMetaData().getURL());
			return build;
		}

		@Primary
		@Bean(name = "localTransactionManager")
		public PlatformTransactionManager
		                transactionManager(@Qualifier("localEntityManagerFactory") EntityManagerFactory emf) {
			JpaTransactionManager txManager = new JpaTransactionManager();
			txManager.setEntityManagerFactory(emf);
			return txManager;
		}
		
		@Primary
		@Bean(name = "localTransactionManager")
		public Map<JpaTransactionManager,EntityManagerFactory>
		                localTransactionManager(@Qualifier("localEntityManagerFactory") EntityManagerFactory emf) {
			JpaTransactionManager txManager = new JpaTransactionManager();
			txManager.setEntityManagerFactory(emf);
			Map map=new HashedMap();
			map.put(txManager,emf);
			return map;
		}
		
	}


