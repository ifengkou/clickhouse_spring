package cn.ifengkou.db.timeseries.config;

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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


/**
 * mapper.mysql 数据源
 * @author shenlongguang<https://github.com/ifengkou>
 * @date: 2019/8/22
 */
@Configuration
@MapperScan(basePackages = {"cn.ifengkou.db.timeseries.dao.mysql"},sqlSessionFactoryRef = "sqlSessionFactory")
public class MysqlDataSourceConfig {
    //private String MYBATIS_CONFIG_FILE = "mybatis-config.xml";

    @Value("${mysql.mapper.location}")
    private String MYSQL_MAPPER_LOCATION;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        DataSource dataSource = DataSourceBuilder.create().driverClassName("com.mysql.jdbc.Driver").build();
        return dataSource;
    }
    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        //sessionFactory.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG_FILE));
        sessionFactory.setDataSource(dataSource);
        //sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MYSQL_MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    @Bean(name = "mysqlJdbcTemplate")
    @Primary
    public JdbcTemplate mysqlJdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
