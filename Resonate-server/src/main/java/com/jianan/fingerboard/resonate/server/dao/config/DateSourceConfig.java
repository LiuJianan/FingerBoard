package com.jianan.fingerboard.resonate.server.dao.config;

import javax.sql.DataSource;

import com.jianan.fingerboard.resonate.server.dao.mapper.UserDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.sql.SQLException;

/**
 * @author by jianan.liu on 17/4/10.
 */
@Configuration
@MapperScan(basePackageClasses = UserDao.class, sqlSessionFactoryRef = "sqlSessionFactory")
public class DateSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(DateSourceConfig.class);
    private static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    @Value("${datasource.url}")
    private String url;

    @Value("${datasource.username}")
    private String user;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.driverClassName}")
    private String driverClass;

    @Bean(name = "masterDataSource")
    @Primary
    public DataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        try {
            dataSource.setFilters("stat");
        } catch (SQLException e) {
            logger.error("data source filter set error", e);
        }
        return dataSource;
    }

    @Bean(name = "masterTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }


    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DateSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
