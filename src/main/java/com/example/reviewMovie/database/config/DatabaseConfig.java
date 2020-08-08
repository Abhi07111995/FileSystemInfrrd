package com.example.reviewMovie.database.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public abstract class DatabaseConfig {

	// @Bean
	public abstract DataSourceProperties dataSourceProperties();

	public HikariDataSource dataSource() {
		DataSourceProperties dataSourceProperties = dataSourceProperties();

		// DataSourceBuilder<?> initializeDataSourceBuilder =
		// dataSourceProperties.initializeDataSourceBuilder();
		/*
		 * HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder
		 * .create(dataSourceProperties.getClassLoader())
		 * .driverClassName(dataSourceProperties.getDriverClassName())
		 * .url(dataSourceProperties.getUrl())
		 * .username(dataSourceProperties.getUsername())
		 * .password(dataSourceProperties.getPassword())
		 * .type(HikariDataSource.class).build(); return dataSource;
		 */
		HikariConfig config = new HikariConfig();
		System.out.println(dataSourceProperties.getUsername()+" : "+ dataSourceProperties.getPassword()
					+ "URL : "+dataSourceProperties.getUrl());
		config.setUsername(dataSourceProperties.getUsername());
		config.setPassword(dataSourceProperties.getPassword());
		config.setJdbcUrl(dataSourceProperties.getUrl());
		config.setMaximumPoolSize(
		                Integer.valueOf(dataSourceProperties.getXa().getProperties().get("maximum-pool-size")));
        config.setMaxLifetime(45000);
		HikariDataSource dataSource = new HikariDataSource(config);

		// DriverManagerDataSource dataSource = new DriverManagerDataSource();

		// connectionProperties.setProperty("maxActive", "0");
		// HikariConfig config = new HikariConfig(props);
		// HikariDataSource ds = new HikariDataSource(config);

		// dataSource.setUrl(dataSourceProperties.getUrl());
		// dataSource.setUsername(dataSourceProperties.getUsername());
		// dataSource.setPassword(dataSourceProperties.getPassword());
		// dataSource.setConnectionProperties(connectionProperties);
		return dataSource;
	}

	protected Map<String, Object> jpaProperties() {
		Map<String, Object> props = new HashMap<>();
		props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
		props.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
		return props;
	}

	@ConfigurationProperties
	protected HibernateJpaVendorAdapter jpaVendor() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		HashMap<String, Object> props = new HashMap<>();
		props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
		props.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
		return vendorAdapter;
	}
}

