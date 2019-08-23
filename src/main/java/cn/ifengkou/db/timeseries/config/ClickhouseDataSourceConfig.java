package cn.ifengkou.db.timeseries.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date: 2019/8/22
 */
@Slf4j
@Configuration
@MapperScan(basePackages = {"cn.ifengkou.db.timeseries.dao.clickhouse"},sqlSessionFactoryRef = "clickHouseSqlSessionFactory")
public class ClickhouseDataSourceConfig {
    @Value("${clickhouse.mapper.location}")
    private String CLICKHOUSE_MAPPER_LOCATION;
    @Bean
    @ConfigurationProperties(prefix = "spring.clickhouse")
    public DataSource clickHouseDataSource() {
        DataSource dataSource =  DataSourceBuilder.create().build();
        return dataSource;
    }
    @Bean(name = "clickHouseSqlSessionFactory")
    public SqlSessionFactory clickHouseSqlSessionFactory(@Qualifier("clickHouseDataSource") DataSource clickHouseDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(clickHouseDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(CLICKHOUSE_MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    @Bean(name = "clickhouseJdbcTemplate")
    public JdbcTemplate prestoJdbcTemplate(@Qualifier("clickHouseDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}
