Spring Data 集成Clickhouse
Mybatis 集成 Clickhouse
多数据源(mysql、clickhouse)集成(cn.ifengkou.db.timeseries.config包下面的MysqlDataSourceConfig和ClickhouseDataSourceConfig)
注解方式、mybatis_mapper方式、jdbcTemplate方式(见EventService)

## 记录点

1. com.github.housepower.jdbc.ClickHouseDriver 无法支持到Spring
2. 更改数据库连接检查sql语句：connection-test-query=SELECT 1
3. 要设置用户名/密码
4. 可以从单元测试往下跟