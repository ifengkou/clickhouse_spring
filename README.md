该工程主要是基础能力，通过几个单元测试类可以往下跟。

## 主要测试的点：

1. Spring Data 集成Clickhouse
2. Mybatis 集成 Clickhouse
3. 多数据源(mysql、clickhouse)集成(cn.ifengkou.db.timeseries.config包下面的MysqlDataSourceConfig和ClickhouseDataSourceConfig)
4. 注解方式、mybatis_mapper方式、jdbcTemplate方式(见EventService)

## 注意点

1. com.github.housepower.jdbc.ClickHouseDriver 无法支持到Spring
2. 更改数据库连接检查sql语句：connection-test-query=SELECT 1
3. 要设置用户名/密码
