package com.cvi.config.datasource;

import com.cvi.config.datasource.ReplicationDataSourceProperties.Slave;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
// @EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(ReplicationDataSourceProperties.class)
@Configuration
public class ReplicationDataSourceConfig {

    private final ReplicationDataSourceProperties dataSourceProperties;
    private final JpaProperties jpaProperties;

    public ReplicationDataSourceConfig(ReplicationDataSourceProperties dataSourceProperties, JpaProperties jpaProperties) {
        this.dataSourceProperties = dataSourceProperties;
        this.jpaProperties = jpaProperties;
    }

    // DataSourceProperties 클래스를 통해 yml 파일에서 읽어들인 DataSource 설정들을 실제 DataSource 객체로 생성 후 ReplicationRoutingDataSource에 등록
    @Bean
    public DataSource routingDataSource() {
        DataSource masterDataSource = createDataSource(
            dataSourceProperties.getDriverClassName(),
            dataSourceProperties.getUrl(),
            dataSourceProperties.getUsername(),
            dataSourceProperties.getPassword()
        );

        Map<Object, Object> dataSources = new LinkedHashMap<>();
        dataSources.put("master", masterDataSource);

        for (Slave slave : dataSourceProperties.getSlaves().values()) {
            DataSource slaveDatSource = createDataSource(
                slave.getDriverClassName(),
                slave.getUrl(),
                slave.getUsername(),
                slave.getPassword()
            );

            dataSources.put(slave.getName(), slaveDatSource);
        }

        ReplicationRoutingDataSource replicationRoutingDataSource = new ReplicationRoutingDataSource();
        replicationRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
        replicationRoutingDataSource.setTargetDataSources(dataSources);

        return replicationRoutingDataSource;
    }

    // DataSource 생성
    public DataSource createDataSource(String driverClassName, String url, String username, String password) {
        return DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .url(url)
            .driverClassName(driverClassName)
            .username(username)
            .password(password)
            .build();
    }

    // 매 쿼리 수행마다 Connection 연결
    @Bean
    public DataSource dataSource() throws SQLException {
        final DataSource targetDataSource = routingDataSource();
        log.info("현재 Datasource : {}", targetDataSource.getConnection().getMetaData().getURL());
        return new LazyConnectionDataSourceProxy(targetDataSource);
    }

    // JPA에서 사용하는 EntityManagerFactory 설정. hibernate 설정을 직접 주입한다.
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws SQLException {
        EntityManagerFactoryBuilder entityManagerFactoryBuilder = createEntityManagerFactoryBuilder(jpaProperties);
        return entityManagerFactoryBuilder.dataSource(dataSource())
            .packages("com.cvi")
            .build();
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties jpaProperties) {
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        return new EntityManagerFactoryBuilder(vendorAdapter, jpaProperties.getProperties(), null);
    }

    // JPA에서 사용할 TransactionManager 설정
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory);
        return tm;
    }
}
